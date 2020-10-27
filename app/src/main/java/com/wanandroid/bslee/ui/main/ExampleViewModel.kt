package com.wanandroid.bslee.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.wanandroid.bslee.vo.BannerVO
import com.wanandroid.bslee.vo.WanAndroidResponse
import com.wanandroid.bslee.base.BaseViewModel

class ExampleViewModel(application: Application) : BaseViewModel(application) {

    private val bannerList: LiveData<WanAndroidResponse<List<BannerVO>>> =
        Transformations.switchMap(refreshTrigger) {
            //当refreshTrigger的值被设置时，bannerList
            api.bannerList()
        }

    val banners: LiveData<List<BannerVO>> = Transformations.map(bannerList) {
        loading.value = false
        it.data ?: ArrayList<BannerVO>()
    }

}
