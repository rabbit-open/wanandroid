package com.wanandroid.bslee

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator


class RetrofitApplication : MultiDexApplication() {

    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(object : DefaultRefreshHeaderCreator {
            override fun createRefreshHeader(
                context: Context,
                layout: RefreshLayout
            ): RefreshHeader {
                layout.setPrimaryColorsId(R.color.colorPrimary, R.color.white)//全局设置主题颜色
                return ClassicsHeader(context);
            }

        })
        SmartRefreshLayout.setDefaultRefreshFooterCreator(object : DefaultRefreshFooterCreator {
            override fun createRefreshFooter(
                context: Context,
                layout: RefreshLayout
            ): RefreshFooter {
                return ClassicsFooter(context).setDrawableSize(20f)
            }
        })
    }

    companion object {
        lateinit var app: RetrofitApplication
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }

}