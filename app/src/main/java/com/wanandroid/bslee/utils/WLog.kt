package com.wanandroid.bslee.utils

import android.util.Log

const val tag = "[WanAndroid]"

fun Any.wLogPrint(msg: String) {
    Log.d("$tag:${this.javaClass.simpleName}", msg);
}

fun Any.wLogPrint(msg: String, throwable: Throwable) {
    Log.d("$tag:${this.javaClass.simpleName}", msg, throwable);
}

fun Any.wLogPrint(msg: String, vararg param: Any) {
    Log.d("$tag:${this.javaClass.simpleName}", msg.plus(":").plus(param.joinToString()));
}