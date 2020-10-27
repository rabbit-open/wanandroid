package com.wanandroid.bslee.model

import androidx.fragment.app.Fragment


class TabItem(
    var icon: Int,
    var name: String,
    var fragmentCls: Class<out Fragment>
)