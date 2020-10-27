package com.dj.coroutines.permisstions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.dj.coroutines.permisstions.callbacks.FailCallback
import com.dj.coroutines.permisstions.callbacks.SuccessCallback
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun FragmentActivity.requestPermissionsForResult(vararg permissions: String): Boolean =
    suspendCoroutine {
        InlinePermissionResult(this)
            .onSuccess(object : SuccessCallback {
                override fun onSuccess() {
                    it.resume(true)
                }
            })
            .onFail(object : FailCallback {
                override fun onFailed() {
                    it.resumeWithException(InlineRequestPermissionException())
                }
            })
            .requestPermissions(*permissions)
    }

suspend fun Fragment.requestPermissionsForResult(vararg permissions: String): Boolean =
    suspendCoroutine {
        InlinePermissionResult(this)
            .onSuccess(object : SuccessCallback {
                override fun onSuccess() {
                    it.resume(true)
                }
            })
            .onFail(object : FailCallback {
                override fun onFailed() {
                    it.resumeWithException(InlineRequestPermissionException())
                }
            })
            .requestPermissions(*permissions)
    }