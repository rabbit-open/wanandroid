package com.wanandroid.bslee.dsl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.wanandroid.bslee.vo.WanAndroidResponse

fun <T> LiveData<WanAndroidResponse<T>>.hideLoading(loading: MutableLiveData<Boolean>): LiveData<WanAndroidResponse<T>> {
    return Transformations.map(this) {
        loading.value = false
        it
    }
}

fun <T> LiveData<WanAndroidResponse<T>>.showLoading(loading: MutableLiveData<Boolean>): LiveData<WanAndroidResponse<T>> {
    return Transformations.map(this) {
        loading.value = true
        it
    }
}
