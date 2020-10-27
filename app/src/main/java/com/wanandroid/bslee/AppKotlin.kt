package com.wanandroid.bslee

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.makeText
import com.bumptech.glide.Glide

@JvmField
val AppContext: RetrofitApplication = RetrofitApplication.app

fun ImageView.displayUrlWithImage(url: String) {
    Glide.with(context).load(url).into(this)
}

fun showToast(tip: String) {
    makeText(AppContext, tip, Toast.LENGTH_SHORT).show()
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}
