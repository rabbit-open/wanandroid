package com.wanandroid.bslee.network

import androidx.lifecycle.LiveData
import com.wanandroid.bslee.vo.WanAndroidResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<T>(private val responseType: Type) : CallAdapter<T, LiveData<T>> {
    override fun adapt(call: Call<T>): LiveData<T> {
        return object : LiveData<T>() {
            private val started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {//确保执行一次
                    call.enqueue(object : Callback<T> {
                        override fun onFailure(call: Call<T>, t: Throwable) {
                            val value = WanAndroidResponse<T>(
                                null,
                                -1,
                                t.message ?: ""
                            ) as T
                            print("failure${value}")
                            postValue(value)
                        }

                        override fun onResponse(call: Call<T>, response: Response<T>) {
                            if (response.code() == 200) {
                                postValue(response.body())
                            } else {
                                val value = WanAndroidResponse<T>(
                                    null,
                                    -1,
                                    response.message() ?: ""
                                ) as T
                                print("Response${value}")
                                postValue(value)
                            }
                        }
                    })
                }
            }
        }
    }

    override fun responseType() = responseType
}