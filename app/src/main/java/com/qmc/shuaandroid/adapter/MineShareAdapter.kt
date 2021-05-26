package com.qmc.shuaandroid.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qmc.network.model.InnerData
import com.qmc.network.model.InnerMineCollect
import com.qmc.network.model.InnerShareArticles
import com.qmc.network.model.MineShareArticleModel
import com.qmc.shuaandroid.R

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/19-16:20
 *@Description
 */
class MineShareAdapter(list: MutableList<InnerShareArticles>) :
    BaseQuickAdapter<InnerShareArticles, BaseViewHolder>(R.layout.adapter_official_item, list) {
    override fun convert(holder: BaseViewHolder, item: InnerShareArticles) {
        holder.apply {
            setText(R.id.title, Html.fromHtml(item.title))
            setText(R.id.niceDate, item.niceDate)
            setText(R.id.author, "分享人:${item.shareUser}")
            setGone(R.id.topTag, item.type != 1)
            setGone(R.id.newTag, !item.fresh)
            setText(R.id.chapterTag, item.chapterName)
            if(item.collect) setImageResource(R.id.like,R.drawable.ic_baseline_favorited_24)
            else setImageResource(R.id.like,R.drawable.ic_baseline_favorite_24)
        }

    }


}