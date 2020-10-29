package com.wanandroid.bslee.ui.collect

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wanandroid.bslee.R
import com.wanandroid.bslee.base.BaseFragment
import com.wanandroid.bslee.network.WanAndroidApi
import kotlinx.android.synthetic.main.collectfragment.*

class CollectViewFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.collectfragment

    val collectAdapter by lazy { CollectViewAdapter() }
    var page = 0;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.layoutManager = LinearLayoutManager(activity)
        list.adapter = collectAdapter
        srlRefreshLay.setEnableLoadMore(true)
        srlRefreshLay.setOnRefreshListener { firstLoad(); }
        srlRefreshLay.setOnLoadMoreListener { moreLoad(); }
        firstLoad();
    }

    private fun firstLoad() {
        page = 0;
        WanAndroidApi.get().collectPage(page).observe(viewLifecycleOwner,
            Observer {
                val newList = if (it.data != null) it.data!!.datas else arrayListOf();
                collectAdapter.data = newList.toMutableList();
                srlRefreshLay.finishRefresh();
                srlRefreshLay.setEnableLoadMore(it.data!!.pageCount > ++page)
            })
    }

    private fun moreLoad() {
        WanAndroidApi.get().collectPage(page).observe(viewLifecycleOwner,
            Observer {
                val newList = if (it.data != null) it.data!!.datas else arrayListOf();
                collectAdapter.data.addAll(newList.toMutableList());
                srlRefreshLay.setEnableLoadMore(it.data!!.pageCount > ++page)
                srlRefreshLay.finishLoadMore();
            })
    }

}
