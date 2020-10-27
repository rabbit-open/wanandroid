package com.wanandroid.bslee.network

import com.wanandroid.bslee.utils.NetworkUtils.isNetworkAvailable
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class CacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        //网络不可用
        if (!isNetworkAvailable()) {
            //在请求头中加入：强制使用缓存，不访问网络
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
        }
        val response: Response = chain.proceed(request)
        //网络可用
        if (isNetworkAvailable()) {
            val maxAge = 0
            // 有网络时 在响应头中加入：设置缓存超时时间0个小时
            response.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()
        } else {
            // 无网络时，在响应头中加入：设置超时为4周
            val maxStale = 60 * 60 * 24 * 28
            response.newBuilder()
                .header(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=$maxStale"
                )
                .build()
        }
        return response
    }
}