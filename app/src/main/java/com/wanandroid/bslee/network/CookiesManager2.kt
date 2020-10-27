package com.wanandroid.bslee.network

import com.wanandroid.bslee.db.SharePrefDb
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * 自动管理Cookies
 */
class CookiesManager2 : CookieJar {

    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
        return SharePrefDb.getCookies()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        if (url.toString().startsWith(
                "https://www.wanandroid.com/user/login?"
            )
        ) {
            SharePrefDb.saveCookies(cookies)
        }
    }
}