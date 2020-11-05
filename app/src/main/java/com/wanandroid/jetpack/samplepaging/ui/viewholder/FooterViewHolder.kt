package com.wanandroid.jetpack.samplepaging.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wanandroid.bslee.R

class FooterViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_footer, parent, false)
) {

    fun bindsFooter() {
        // empty implementation
    }
}