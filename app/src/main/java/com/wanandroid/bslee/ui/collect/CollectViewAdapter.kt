package com.wanandroid.bslee.ui.collect

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wanandroid.bslee.R
import com.wanandroid.bslee.ui.web.WebActivity
import com.wanandroid.bslee.vo.ArticleVO
import kotlinx.android.synthetic.main.item_text.view.*

class CollectViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = mutableListOf<ArticleVO>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.apply {
            val vo = data.get(position) as ArticleVO
            text.text = vo.title
            setOnClickListener {
                vo.link?.let { WebActivity.nav(vo.chapterId, it, context) }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = from(parent.context).inflate(R.layout.item_text, parent, false);
        return object : RecyclerView.ViewHolder(itemView) {}
    }

}