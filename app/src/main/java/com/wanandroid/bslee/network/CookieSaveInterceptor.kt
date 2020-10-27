package com.wanandroid.bslee.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class CookieSaveInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val resp = chain.proceed(chain.request());
        val cookies = resp.headers("Set-Cookie");
        var cookieStr = "";
        if (cookies.size > 0) {
            cookies.forEach {
                cookieStr += it
            }
            UserUtil.saveUserCookieId(cookieStr);
        }
        return resp;
    }
}