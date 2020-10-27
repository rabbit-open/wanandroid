package com.wanandroid.bslee.ui.me

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.wanandroid.bslee.R
import com.wanandroid.bslee.RetrofitApplication
import com.wanandroid.bslee.base.BaseFragment
import com.wanandroid.bslee.db.SharePrefDb
import com.wanandroid.bslee.db.SharePrefUserDb
import com.wanandroid.bslee.gone
import com.wanandroid.bslee.observer.ResponseObserver
import com.wanandroid.bslee.visible
import com.wanandroid.bslee.vo.LoginUserVO
import kotlinx.android.synthetic.main.me_fragment.*

class MeFragment : BaseFragment() {


    override val layoutId: Int
        get() = R.layout.me_fragment

    companion object {
        fun newInstance() = MeFragment()
    }

    private lateinit var viewModel: MeViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider.AndroidViewModelFactory(RetrofitApplication.app!!)
            .create(MeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.attachLoadingObserver(loadingState)
        viewModel.logout.observe(viewLifecycleOwner, object : ResponseObserver<Unit>() {
            override fun onSuccess(t: Unit?) {
                SharePrefDb.logout()
                exitLogout()
            }
        })
        viewModel.user.observe(viewLifecycleOwner, object : ResponseObserver<LoginUserVO>() {
            override fun onSuccess(t: LoginUserVO?) {
                t?.run {
                    SharePrefUserDb.saveUserInfo(t.username, t.nickname)
                    loginSuccess()
                }
            }
        })
        btnLogin.setOnClickListener {
            viewModel.requestLoginData(etUserName.text.toString(), etPassWord.text.toString())
        }
        tvLogout.setOnClickListener {
            viewModel.logout()
        }

        if (!SharePrefUserDb.isLogin()) {
            exitLogout()
        } else {
            loginSuccess()
        }
    }

    private fun exitLogout() {
        loginLay.gone()
        nologinLay.visible()
    }

    private fun loginSuccess() {
        tvUserName.text = SharePrefDb.getString(SharePrefDb.KEY_NICK_NAME);
        nologinLay.gone()
        loginLay.visible()
    }
}