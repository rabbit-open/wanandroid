package com.wanandroid.bslee.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.wanandroid.bslee.observer.LoadingObserver

open abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int
    val loadingState = MutableLiveData<Boolean>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadingState.observe(viewLifecycleOwner, LoadingObserver(activity!!))
    }

}