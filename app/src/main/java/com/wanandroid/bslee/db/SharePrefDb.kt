package com.wanandroid.bslee.db

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import com.wanandroid.bslee.AppContext
import okhttp3.Cookie


object SharePrefDb {

    val KEY_IS_LOGIN = "isLogin"
    val KEY_NICK_NAME = "nickName"
    val KEY_USER_NAME = "username"
    val KEY_DOMAIN = "domain"

    fun getWanConfig(): SharedPreferences {
        return AppContext.getSharedPreferences(
            "wanConfig",
            Context.MODE_PRIVATE
        )
    }

    fun getCookieSpref(): SharedPreferences {
        return AppContext.getSharedPreferences(
            "cookies",
            Context.MODE_PRIVATE
        )
    }

    fun put(key: String, value: String) {
        getWanConfig().edit().putString(key, value).apply()
    }

    fun put(key: String, value: Boolean) {
        getWanConfig().edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return getWanConfig().getBoolean(key, false)
    }

    fun getString(key: String): String? {
        return getWanConfig().getString(key, "")
    }

    fun saveCookies(cookies: List<Cookie>) {
        val editor = getCookieSpref().edit()
        val domain = "www.wanandroid.com"
        val cookie = StringBuilder()
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookies.forEach {
            editor.putString(it.name, it.value)
            cookie.append(it.name).append("=").append(it.value).append(";")
            cookieManager.setCookie(it.domain, "${it.name}=${it.value}")
        }
        editor.apply()
        SharePrefDb.put(KEY_DOMAIN, domain)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(AppContext)
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush()
        }
    }

    fun getCookies(): ArrayList<Cookie> {
        val sp = getCookieSpref()
        val domain = getString(KEY_DOMAIN)
        return ArrayList<Cookie>().apply {
            val names = sp.all.keys
            names.forEach {
                add(
                    Cookie.Builder()
                        .domain(domain!!)
                        .name(it)
                        .value(sp.getString(it, "")!!)
                        .build()
                )
            }
        }
    }

    fun logout() {
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookies(null)
            cookieManager.removeAllCookie()
            cookieManager.flush()
        } else {
            cookieManager.removeSessionCookie()
            cookieManager.removeAllCookie()
            CookieSyncManager.getInstance().sync()
        }
        SharePrefDb.put(KEY_IS_LOGIN, false)
        SharePrefDb.put(KEY_NICK_NAME, "")
        SharePrefDb.put(KEY_USER_NAME, "")
    }
}