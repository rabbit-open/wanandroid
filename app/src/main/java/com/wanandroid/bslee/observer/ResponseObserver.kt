package com.wanandroid.bslee.observer

import androidx.lifecycle.Observer
import com.wanandroid.bslee.db.SharePrefDb
import com.wanandroid.bslee.showToast
import com.wanandroid.bslee.vo.WanAndroidResponse

open class ResponseObserver<T> : Observer<WanAndroidResponse<T>> {

    override fun onChanged(t: WanAndroidResponse<T>) {
        if (t.errorCode == 0) {
            onSuccess(t.data)
        } else {
            if (t.errorCode == -1001) {
                onLogin(t.errorMsg)
            } else {
                onFail(Exception(t.errorMsg))
            }
        }
    }

    open fun onFail(exception: Exception) {
        showToast(exception.toString())
    }

    open fun onLogin(msg: String?) {
        showToast(msg ?: "")
        SharePrefDb.logout()
    }

    open fun onSuccess(t: T?) {

    }
}