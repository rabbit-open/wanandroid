package com.wanandroid.bslee.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wanandroid.bslee.R
import com.wanandroid.bslee.RetrofitApplication
import com.wanandroid.bslee.adapter.ArticleAdapter
import com.wanandroid.bslee.databinding.PageFragmentBinding
import com.wanandroid.bslee.dsl.config
import com.wanandroid.bslee.vo.BannerVO

class ExamplePageFragment : Fragment() {

    companion object {
        fun newInstance() = ExamplePageFragment()
    }

    private lateinit var viewModel: ExamplePageViewModel
    private lateinit var binding: PageFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.page_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider.AndroidViewModelFactory(RetrofitApplication.app!!)
            .create(ExamplePageViewModel::class.java)
    }

    val adapter = ArticleAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind<PageFragmentBinding>(view)!!
        binding.run {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            executePendingBindings()
        }
        initBanner();
        initRecyclerView();
    }

    private fun initBanner() {
        binding.run {
            banner.config(activity!!)
            vm?.banners!!.observe(viewLifecycleOwner, Observer<List<BannerVO>> {
                banner.setData(it, null)
            })
        }
    }

    private fun initRecyclerView() {
        binding.run {
            recyclerView.let {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(activity!!)
            }
            vm?.articlePage?.observe(viewLifecycleOwner, Observer {
                it?.run {
                    if (curPage == 1) {
                        adapter.addAll(datas, true)
                    } else {
                        adapter.addAll(datas, false)
                    }
                }
            })
            refreshLayout.autoRefresh()
        }
    }


}
