package com.wanandroid.bslee.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.wanandroid.bslee.observer.LoadingObserver
import com.wanandroid.bslee.utils.wLogPrint

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
        wLogPrint("onActivityCreated")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        wLogPrint("onHiddenChanged", hidden)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        wLogPrint("setUserVisibleHint", isVisibleToUser)
    }

    override fun onResume() {
        super.onResume()
        wLogPrint("onResume")
    }

    override fun onPause() {
        super.onPause()
        wLogPrint("onPause")
    }

}