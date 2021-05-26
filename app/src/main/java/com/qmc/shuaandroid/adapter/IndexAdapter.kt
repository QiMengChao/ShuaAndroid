package com.qmc.shuaandroid.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qmc.network.model.InnerData
import com.qmc.shuaandroid.R

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/19-16:20
 *@Description
 */
class IndexAdapter(layoutId: Int, list: List<InnerData>) :
    BaseQuickAdapter<InnerData, BaseViewHolder>(layoutId, list.toMutableList()) {
    override fun convert(holder: BaseViewHolder, item: InnerData) {
        holder.apply {
            setText(R.id.title, Html.fromHtml(item.title))
            setText(R.id.niceDate, item.niceDate)
            setText(R.id.author, item.author)
            setGone(R.id.topTag, item.type != 1)
            setGone(R.id.newTag, !item.fresh)
            setText(R.id.chapterTag, item.chapterName)
            if(item.collect) setImageResource(R.id.like,R.drawable.ic_baseline_favorited_24)
            else setImageResource(R.id.like,R.drawable.ic_baseline_favorite_24)
        }

    }


}