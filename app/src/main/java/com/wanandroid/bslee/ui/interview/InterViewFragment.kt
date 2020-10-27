package com.wanandroid.bslee.ui.interview

import android.os.Bundle
import android.view.View
import com.wanandroid.bslee.R
import com.wanandroid.bslee.base.BaseFragment
import kotlinx.android.synthetic.main.interview_fragment.*

class InterViewFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.interview_fragment

    companion object {
        fun newInstance() = InterViewFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.adapter = InterViewAdapter()
    }

}
