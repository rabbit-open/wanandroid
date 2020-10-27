package com.wanandroid.bslee.dsl

import android.app.Activity
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.wanandroid.bslee.displayUrlWithImage
import com.wanandroid.bslee.ui.web.WebActivity
import com.wanandroid.bslee.vo.BannerVO

fun BGABanner.config(activity: Activity) {
    setDelegate(object : BGABanner.Delegate<ImageView, BannerVO> {
        override fun onBannerItemClick(
            banner: BGABanner?,
            itemView: ImageView?,
            model: BannerVO?,
            position: Int
        ) {
            WebActivity.nav(0,model!!.url, activity)
        }

    })
    val bannerAdapter = BGABanner.Adapter<ImageView, BannerVO> { _, image, model, _ ->
        image.displayUrlWithImage(model!!.imagePath ?: "")
    }
    setAdapter(bannerAdapter)
}