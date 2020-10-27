package com.wanandroid.bslee.ui.collect

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.wanandroid.bslee.R
import com.wanandroid.bslee.ui.web.WebActivity
import com.wanandroid.bslee.vo.ArticleVO
import kotlinx.android.synthetic.main.item_text.view.*

class CollectViewAdapter : BaseAdapter() {

    var data: ArrayList<ArticleVO> = arrayListOf()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return convertView ?: LayoutInflater.from(parent!!.context)
            .inflate(R.layout.item_text, parent, false).apply {
                val vo = getItem(position) as ArticleVO
                text.text = vo.title
                this.setOnClickListener {
                    vo.link?.let { WebActivity.nav(vo.chapterId, it, context) }
                }
            }
    }

    override fun getItem(position: Int): Any {
        return data.get(position);
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getCount(): Int {
        return data.size;
    }

    fun addHome(list: List<ArticleVO>?) {
        this.data.clear()
        this.data.addAll(list ?: arrayListOf())
        notifyDataSetChanged()
    }

}