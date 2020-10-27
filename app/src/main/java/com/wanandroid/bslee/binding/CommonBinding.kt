package com.wanandroid.bslee.binding

import android.app.Activity
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

@BindingAdapter(value = ["refreshing", "moreLoading", "hasMore"], requireAll = false)
fun bindSmartRefreshLayout(
    smartLayout: SmartRefreshLayout,
    refreshing: Boolean,
    moreLoading: Boolean,
    hasMore: Boolean

) {
    if (!refreshing) smartLayout.finishRefresh()
    if (!moreLoading) smartLayout.finishLoadMore()
    smartLayout.setEnableLoadMore(hasMore)
}

@BindingAdapter(value = ["onRefreshListener", "onLoadMoreListener"], requireAll = false)
fun bindListener(
    smartLayout: SmartRefreshLayout,
    refreshListener: OnRefreshListener?,
    loadMoreListener: OnLoadMoreListener?
) {
    smartLayout.setOnRefreshListener(refreshListener)
    smartLayout.setOnLoadMoreListener(loadMoreListener)
}


@BindingAdapter(value = ["imageId"])
fun bindImage(iv: ImageView, id: Int?) {
    if (id != null)
        iv.setImageResource(id)
}

@BindingAdapter(value = ["select"])
fun bindSelect(v: View, select: Boolean) {
    v.isSelected = select
}

@BindingAdapter(value = ["back"])
fun bindBackAction(v: View, select: Boolean) {
    if (select) {
        v.setOnClickListener {
            (v.context as Activity).finish()
        }
    }
}

@BindingAdapter(value = ["html"])
fun bindHtml(tv: TextView, text: String) {
    tv.text = Html.fromHtml(text)
}
