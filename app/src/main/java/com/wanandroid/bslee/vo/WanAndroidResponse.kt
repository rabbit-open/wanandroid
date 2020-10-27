package com.wanandroid.bslee.vo

class WanAndroidResponse<T>(
    var data: T?,
    var errorCode: Int?,//0 成功 非0都是错误
    var errorMsg: String?
)