package com.wanandroid.bslee.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wanandroid.bslee.R
import com.wanandroid.bslee.RetrofitApplication
import com.wanandroid.bslee.base.BaseFragment
import com.wanandroid.bslee.dsl.config
import com.wanandroid.bslee.vo.BannerVO
import kotlinx.android.synthetic.main.main_fragment.*

class ExampleFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.main_fragment

    companion object {
        fun newInstance() = ExampleFragment()
    }

    private lateinit var viewModel: ExampleViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider.AndroidViewModelFactory(RetrofitApplication.app!!)
            .create(ExampleViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.run {
            banner.config(activity!!)
            srlRefresh.config(viewLifecycleOwner, loadingState, {
                loadData()
            })
            attachLoadingObserver(loadingState)
            banners.observe(viewLifecycleOwner, Observer<List<BannerVO>> {
                banner.setData(it, null)
            })
            loadData()
        }
    }

}
