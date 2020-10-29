package com.wanandroid.bslee.ui.collect

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.wanandroid.bslee.R
import com.wanandroid.bslee.base.BaseFragment
import com.wanandroid.bslee.network.WanAndroidApi
import com.wanandroid.bslee.observer.ResponseObserver
import com.wanandroid.bslee.vo.ArticleVO
import com.wanandroid.bslee.vo.PageVO
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
            object : ResponseObserver<PageVO<ArticleVO>>() {
                override fun onSuccess(t: PageVO<ArticleVO>?) {
                    t?.apply {
                        collectAdapter.data = t.datas.toMutableList();
                        srlRefreshLay.run {
                            finishRefresh();
                            setEnableLoadMore(pageCount > ++page)
                        }
                    }
                }
            })
    }

    private fun moreLoad() {
        WanAndroidApi.get().collectPage(page).observe(viewLifecycleOwner,
            object : ResponseObserver<PageVO<ArticleVO>>() {
                override fun onSuccess(t: PageVO<ArticleVO>?) {
                    t?.run {
                        collectAdapter.data.addAll(t.datas.toMutableList());
                        srlRefreshLay.setEnableLoadMore(t.pageCount > ++page)
                        srlRefreshLay.finishLoadMore();
                    }
                }
            })
    }

}
