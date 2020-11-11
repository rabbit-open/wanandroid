package com.wanandroid.jetpack.samplepaging.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wanandroid.jetpack.samplepaging.R


class HeaderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
) {

    fun bindsHeader() {
        // empty implementation
    }
}