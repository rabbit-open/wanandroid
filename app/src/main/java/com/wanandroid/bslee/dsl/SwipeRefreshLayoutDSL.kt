package com.wanandroid.bslee.dsl

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

fun SwipeRefreshLayout.config(
    viewLifecycleOwner: LifecycleOwner,
    loadingState: MutableLiveData<Boolean>,
    block: () -> Unit
) {
    loadingState.observe(viewLifecycleOwner, Observer<Boolean> {
        isRefreshing = it
    })
    setOnRefreshListener {
        block()
    }
}

