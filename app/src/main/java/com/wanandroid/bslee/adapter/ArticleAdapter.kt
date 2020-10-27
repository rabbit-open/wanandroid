package com.wanandroid.bslee.adapter

import com.wanandroid.bslee.R
import com.wanandroid.bslee.databinding.ItemArticleBinding
import com.wanandroid.bslee.ui.web.WebActivity
import com.wanandroid.bslee.vo.ArticleVO

class ArticleAdapter : DataBoundAdapter<ArticleVO, ItemArticleBinding>() {
    //private val dao = AppDataBase.get().historyDao()
    //var username = SP.getString(SP.KEY_USER_NAME)

    //init {//监听登录变化
        // LiveDataBus.username.observeForever {
        //     username = it
        //     mData.forEach { a ->
        //         a.read = dao.isRead(it, a.id)
        //    }
        //    notifyDataSetChanged()
        // }
   // }

    override fun initView(binding: ItemArticleBinding, item: ArticleVO) {
        binding.vo = item
        binding.root.setOnClickListener {
            //  val history =
            //      HistoryArticleVO(item.id, username, System.currentTimeMillis())
            //   dao.addArticle(item)
            //   dao.addHistory(history)
            item.read = true
            notifyDataSetChanged()
            WebActivity.nav(item.id,item.link,it.context)
        }
    }

    override val layoutId: Int
        get() = R.layout.item_article
}