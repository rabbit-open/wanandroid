package com.wanandroid.bslee.ui.interview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.wanandroid.bslee.R
import com.wanandroid.bslee.ui.web.WebActivity
import kotlinx.android.synthetic.main.item_text.view.*

class InterViewAdapter : BaseAdapter() {

    var data: ArrayList<InterViewVo> = arrayListOf(
        InterViewVo("个人简书博客", "https://www.jianshu.com/p/ceb243e45450"),
        InterViewVo("个人github", "https://github.com/lihongjiang"),
        InterViewVo("个人Jfrog仓库地址", "https://dl.bintray.com/lihongjiang/maven/"),
        InterViewVo("个人github网站", "https://lihongjiang.github.io/"),
        InterViewVo("", ""),
        InterViewVo("MVC、MVP、MVVM，我到底该怎么选？", "https://blog.csdn.net/singwhatiwanna/article/details/80904132"),
        InterViewVo("Android性能优化", "https://www.jianshu.com/p/754f7607c869"),
        InterViewVo("面试：Object方法有哪些?", "https://www.jianshu.com/p/50cde46b2c05"),
        InterViewVo("2020年Android面试题汇总（初级）", "https://www.jianshu.com/p/feb9584b492c"),
        InterViewVo("2020年Android面试题汇总（中级）", "https://www.jianshu.com/p/c7b6c6851231")
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return convertView ?: LayoutInflater.from(parent!!.context)
            .inflate(R.layout.item_text, parent, false).apply {
                val vo = getItem(position) as InterViewVo
                text.text = vo.title
                this.setOnClickListener {
                    vo.url?.let { WebActivity.nav(0,it, context) }
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

}