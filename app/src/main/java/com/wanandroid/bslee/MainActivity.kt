package com.wanandroid.bslee

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dj.coroutines.permisstions.InlineRequestPermissionException
import com.dj.coroutines.permisstions.requestPermissionsForResult
import com.google.android.material.tabs.TabLayout
import com.wanandroid.bslee.base.BaseActivity
import com.wanandroid.bslee.compat.OverLayWindowCompact
import com.wanandroid.bslee.databinding.MainActivityBinding
import com.wanandroid.bslee.model.TabItem
import com.wanandroid.bslee.ui.collect.CollectViewFragment
import com.wanandroid.bslee.ui.interview.InterViewFragment
import com.wanandroid.bslee.ui.main.ExamplePageFragment
import com.wanandroid.bslee.ui.me.MeFragment
import kotlinx.android.synthetic.main.view_tab.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : BaseActivity<MainActivityBinding>() {

    override val layoutId: Int
        get() = R.layout.main_activity

    private val tabs = arrayOf(
        TabItem(R.drawable.tab_home, "玩安卓", ExamplePageFragment::class.java),
        TabItem(R.drawable.tab_project, "面试", InterViewFragment::class.java),
        TabItem(R.drawable.tab_wx, "收藏", CollectViewFragment::class.java),
        TabItem(R.drawable.tab_mine, "我", MeFragment::class.java)
    )

    private val fragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragments()
        initTabLayout()
        requestQuanXian()
    }

    private val permissions =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
            )
        } else {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }

    fun requestQuanXian() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                requestPermissionsForResult(*permissions)
                OverLayWindowCompact.requestSettingCanDrawOverlays(this@MainActivity);
                Toast.makeText(this@MainActivity, "权限请求成功", Toast.LENGTH_SHORT).show()
            } catch (e: InlineRequestPermissionException) {
                Toast.makeText(this@MainActivity, "权限请求失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initFragments() {
        if (fragments.isEmpty()) {
            tabs.forEach {
                val f = it.fragmentCls.newInstance()
                fragments.add(f)
            }
        }
    }

    private fun initTabLayout() {
        binding.tabLayout.run {
            tabs.forEach {
                addTab(newTab().setCustomView(getTabView(it)))
            }

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(p0: TabLayout.Tab?) {}

                override fun onTabUnselected(p0: TabLayout.Tab?) {}

                override fun onTabSelected(p0: TabLayout.Tab) {
                    initTab(p0.position)
                }
            })
            getTabAt(0)?.select()
        }
        initTab(0)
    }

    private fun getTabView(it: TabItem): View {
        val v = LayoutInflater.from(this).inflate(R.layout.view_tab, null)
        v.tab_icon.setImageResource(it.icon)
        v.tab_name.text = it.name
        return v
    }

    private fun initTab(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        fragments.forEachIndexed { index, fragment ->
            val tag = fragment.javaClass.simpleName;
            if (index == position) {
                supportFragmentManager.findFragmentByTag(tag)?.apply {
                    transaction.show(this);
                } ?: let {
                    transaction.add(R.id.fl_content, fragment, tag)
                }
            } else {
                supportFragmentManager.findFragmentByTag(tag)?.apply {
                    transaction.hide(this);
                }
            }
        }
        transaction.commit()
    }

}
