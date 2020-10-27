package com.wanandroid.bslee.network

object UserUtil {

    var cacheCookie: String? = null;

    fun saveUserCookieId(cookie: String) {
        cacheCookie = cookie
    }

    fun getCookieId(): String? {
        return cacheCookie
    }

}