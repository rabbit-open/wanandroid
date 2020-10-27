package com.wanandroid.bslee.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.wanandroid.bslee.network.WanAndroidApi

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val refreshTrigger = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    protected val api = WanAndroidApi.get()

    protected val page = MutableLiveData<Int>()
    val refreshing = MutableLiveData<Boolean>()
    val moreLoading = MutableLiveData<Boolean>()
    val hasMore = MutableLiveData<Boolean>()
    val autoRefresh = MutableLiveData<Boolean>()//SmartRefreshLayout自动刷新标记


    //val isLogin = MutableLiveData<Boolean>()
    //val toLogin = MutableLiveData<Boolean>()

    //init {
    //isLogin.value = SP.getBoolean(SP.KEY_IS_LOGIN)
    //LiveDataBus.username.observeForever {
    //监听登录变化
    //    isLogin.value = !TextUtils.isEmpty(it)
    //}
    // }

//    fun isNotLogin(): Boolean {
//        return if (isLogin.value == true) false
//        else {
//            toLogin.value = true
//            true
//        }
//    }

    fun loadMore() {
        page.value = (page.value ?: 0) + 1
        moreLoading.value = true
    }

    /**
     * 自动刷新
     */
    fun autoRefresh() {
        autoRefresh.value = true
    }

    open fun refresh() {
        page.value = 0
        loading.value = true
    }

    open fun loadData() {
        refreshTrigger.value = true
        loading.value = true
    }

    open fun showLoading() {
        loading.value = true
    }

    open fun hideLoading() {
        loading.value = false
    }

    fun attachLoadingObserver(otherLoadingState: MutableLiveData<Boolean>) {
        loading.observeForever {
            otherLoadingState.value = it
        }
    }
}