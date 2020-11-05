package com.wanandroid.jetpack.samplepaging.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wanandroid.jetpack.samplepaging.ui.header_proxy.HeaderProxyActivity
import com.wanandroid.bslee.R
import com.wanandroid.jetpack.samplepaging.ui.basic.BasicUsageActivity
import com.wanandroid.jetpack.samplepaging.ui.header_simple.HeaderSimpleActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBtnBasicUsage.setOnClickListener {
            startActivity(Intent(this, BasicUsageActivity::class.java))
        }
        mBtnHeaderMultiType.setOnClickListener {
            startActivity(Intent(this, HeaderSimpleActivity::class.java))
        }
        mBtnHeaderProxy.setOnClickListener {
            startActivity(Intent(this, HeaderProxyActivity::class.java))
        }
    }
}
