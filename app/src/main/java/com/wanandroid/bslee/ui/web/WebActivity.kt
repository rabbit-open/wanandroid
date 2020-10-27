package com.wanandroid.bslee.ui.web

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kaopiz.kprogresshud.KProgressHUD
import com.wanandroid.bslee.BuildConfig
import com.wanandroid.bslee.R
import com.wanandroid.bslee.network.WanAndroidApi
import com.wanandroid.bslee.showToast
import com.wanandroid.bslee.utils.ShareUtils
import com.wanandroid.bslee.web.WanObject
import com.wanandroid.bslee.web.WebViewClientFactory
import com.wanandroid.bslee.widget.WanWebView
import kotlinx.android.synthetic.main.web_activity.*

class WebActivity : AppCompatActivity() {

    companion object {
        fun nav(id: Int?, link: String?, context: Context) {
            if (!link.isNullOrEmpty()) {
                val intent = Intent(context, WebActivity::class.java)
                intent.putExtra("link", link)
                intent.putExtra("id", id)
                if (context is Activity) {
                    context.startActivityForResult(intent, 1)
                }
            }
        }
    }

    var id: Int = 0
    var link: String = "";
    lateinit var dialog: KProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_activity)
        dialog = KProgressHUD(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(false)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)

        loaded.observe(this, Observer<Boolean> {
            if (it == null) return@Observer
            if (it) {
                dialog.dismiss()
            } else {
                dialog.show()
            }
        })
        loaded.postValue(false)

        intent!!.extras?.getString(Intent.EXTRA_TEXT)?.also {
            link = it
        }
        try {
            intent!!.extras?.getParcelable<Uri>(Intent.EXTRA_STREAM)?.also {
                getRealPathFromURI(this@WebActivity, it)?.also {
                    link = it;
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (link.isNullOrEmpty()) {
            link = intent!!.getStringExtra("link") ?: ""
        }
        id = intent!!.getIntExtra("id", 0)

        initWebView();
        back.setOnClickListener { finish() }
        share.setOnClickListener { ShareUtils.shareText(this, navTitle + link) }
        collect.setOnClickListener {
            if (id!! > 0) {
                WanAndroidApi.get().collect(id!!).observe(this,
                    Observer {
                        if (it.errorCode == 0) {
                            showToast("站内收藏成功")
                        } else {
                            showToast(it.errorMsg ?: "")
                        }
                    })
            } else {
                WanAndroidApi.get().collect(navTitle, "来自网页收藏", link).observe(
                    this,
                    Observer {
                        if (it.errorCode == 0) {
                            showToast("站外收藏成功")
                        } else {
                            showToast(it.errorMsg ?: "")
                        }
                    })
            }
        }
    }

    private fun getRealPathFromURI(webActivity: WebActivity, contentUri: Uri): String? {
        val proj = arrayOf<String>(MediaStore.Images.Media.DATA)
        val cursor = webActivity.managedQuery(contentUri, proj, null, null, null)
            ?: return contentUri.getPath()
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }


    var navTitle = "";
    val loaded = MutableLiveData<Boolean>()

    @SuppressLint("ObsoleteSdkInt")
    private fun initWebView() {
        webView.settings.run {
            // 设置可以支持缩放
            setSupportZoom(true)
            // 设置出现缩放工具
            builtInZoomControls = true
            //扩大比例的缩放
            useWideViewPort = true
            //自适应屏幕
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            loadWithOverviewMode = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                displayZoomControls = false
            }
        }

        webView.settings.run {
            javaScriptEnabled = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
        webView.run {
            setBackgroundColor(0)
            loadUrl(link.replaceBefore("http", ""))
            webViewClient = WebViewClientFactory.create(url ?: "", loaded)
            webChromeClient = object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, t: String?) {
                    super.onReceivedTitle(view, t)
                    navTitle = t ?: ""
                }
            }
            addJavascriptInterface(WanObject(this@WebActivity), "android")
            scrollListener = object : WanWebView.OnScrollChangedListener {
                override fun onScroll(dx: Int, dy: Int, oldX: Int, oldY: Int) {
                    tvtitle.text = if (dy < 10) "" else navTitle
                }

            }
            checkHeightHandler.sendEmptyMessageDelayed(1, 60)

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
        }
        if (link!!.contains(WebViewClientFactory.WAN_ANDROID)) {
            loaded.observe(this, Observer {
                webView.loadUrl(getScript())
            })
        }
    }

    private var checkHeightHandler = @SuppressLint("HandlerLeak")
    object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (loaded.value == true) {
                return
            }
            checkWebHeight()
            sendEmptyMessageDelayed(1, 60)
        }
    }

    private fun checkWebHeight() {//检查内容高度，隐藏加载进度
        webView.run {
            if (contentHeight > height / 2) {
                loaded.value = true
            }
        }
    }

    /**
     * 问答点赞功能
     */
    private fun getScript(): String {
        val id = link!!.substring(link!!.lastIndexOf("/") + 1)
        return """
        javascript:(function(){
            console.log(">>>>>>>>>>>>>>>>>>>>>");
            $(".zan_show").unbind();
            $(".zan_show").click(function(){
                var reg = /loginUserName=/g;
                var cookie = document.cookie;
                if(reg.test(cookie)){
                    var cid = $(this).attr("cid");
    		        var isZan = $(this).attr("is_zan");
                    ajaxZan(cid,"$id",isZan != 1);
                }else{
                    android.toLogin();
                }
            });
        })();
    """.trimIndent()
    }
}