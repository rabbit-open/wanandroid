package com.wanandroid.bslee.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class CookieSetInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val cookieStr = UserUtil.getCookieId();
        if (cookieStr.isNullOrEmpty()) {
            return chain.proceed(
                chain.request().newBuilder().header("Cookie", cookieStr!!).build()
            );
        }
        return chain.proceed(chain.request());
    }
}

