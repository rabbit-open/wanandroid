package com.wanandroid.bslee.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.wanandroid.bslee.vo.BannerVO
import com.wanandroid.bslee.vo.WanAndroidResponse
import com.wanandroid.bslee.base.BaseViewModel

class ExamplePageViewModel(application: Application) : BaseViewModel(application) {

    private val bannerList: LiveData<WanAndroidResponse<List<BannerVO>>> =
        Transformations.switchMap(refreshTrigger) {
            //当refreshTrigger的值被设置时，bannerList
            api.bannerList()
        }

    val banners: LiveData<List<BannerVO>> = Transformations.map(bannerList) {
        loading.value = false
        it.data ?: ArrayList<BannerVO>()
    }

    override fun refresh() {
        super.refresh()
        loadBanner();
    }

    fun loadBanner() {
        refreshTrigger.value = true
        loading.value = true
    }

    private val articleList = Transformations.switchMap(page) {
        api.articleList(it)
    }

    val articlePage = Transformations.map(articleList) {
        refreshing.value = false
        moreLoading.value = false
        hasMore.value = !(it?.data?.over ?: false)
        it.data
    }

}
