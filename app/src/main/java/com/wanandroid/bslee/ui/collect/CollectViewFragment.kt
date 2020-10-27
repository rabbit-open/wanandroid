package com.wanandroid.bslee.ui.collect

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.wanandroid.bslee.R
import com.wanandroid.bslee.base.BaseFragment
import com.wanandroid.bslee.network.WanAndroidApi
import kotlinx.android.synthetic.main.interview_fragment.*

class CollectViewFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.interview_fragment

    companion object {
        fun newInstance() = CollectViewFragment()
    }

    val collectAdapter = CollectViewAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.adapter = collectAdapter

        WanAndroidApi.get().collectPage(0).observe(viewLifecycleOwner,
            Observer {
                collectAdapter.addHome(if (it.data != null) it.data!!.datas else arrayListOf())
            })
    }

}
