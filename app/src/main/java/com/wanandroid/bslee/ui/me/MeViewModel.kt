package com.wanandroid.bslee.ui.me

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.wanandroid.bslee.base.BaseViewModel
import com.wanandroid.bslee.dsl.hideLoading
import com.wanandroid.bslee.vo.LoginUserVO
import com.wanandroid.bslee.vo.WanAndroidResponse

class MeViewModel(application: Application) : BaseViewModel(application) {

    var userName: String? = null
    var passWord: String? = null

    val user: LiveData<WanAndroidResponse<LoginUserVO>> =
        with(refreshTrigger) {
            Transformations.switchMap(this) {
                api.login(userName, passWord)
            }
        }.hideLoading(loading)

    fun requestLoginData(userName: String, password: String) {
        this.userName = userName
        this.passWord = password
        loadData();
    }

    val logOutFlag = MediatorLiveData<Boolean>()
    val logout: LiveData<WanAndroidResponse<Unit>> =
        with(logOutFlag) {
            Transformations.switchMap(this) {
                api.logout().hideLoading(loading)
            }
        }

    fun logout() {
        showLoading()
        logOutFlag.value = true
    }

}