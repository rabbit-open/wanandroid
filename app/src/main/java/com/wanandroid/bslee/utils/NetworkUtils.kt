package com.wanandroid.bslee.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.wanandroid.bslee.AppContext

object NetworkUtils {

    fun isNetworkAvailable(): Boolean {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        val connectivityManager =
            AppContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // 获取NetworkInfo对象
        val networkInfo = connectivityManager.allNetworkInfo
        if (networkInfo.isNotEmpty()) {
            for (i in networkInfo.indices) {
                //println(i.toString() + "===状态===" + networkInfo[i].state)
                //println(i.toString() + "===类型===" + networkInfo[i].typeName)
                // 判断当前网络状态是否为连接状态
                if (networkInfo[i].state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
        }
        return false
    }
}