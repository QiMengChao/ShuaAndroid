package com.qmc.shuaandroid.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qmc.network.model.InnerData
import com.qmc.network.model.InnerMineCollect
import com.qmc.shuaandroid.R

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/19-16:20
 *@Description
 */
class MineCollectAdapter(list: MutableList<InnerMineCollect>) :
    BaseQuickAdapter<InnerMineCollect, BaseViewHolder>(R.layout.adapter_official_item, list) {
    override fun convert(holder: BaseViewHolder, item: InnerMineCollect) {
        holder.apply {
            setText(R.id.title, Html.fromHtml(item.title))
            setText(R.id.niceDate, item.niceDate)
            setText(R.id.author, item.author)
            setGone(R.id.topTag, true)
            setGone(R.id.newTag, true)
            setText(R.id.chapterTag, item.chapterName)
            setImageResource(R.id.like,R.drawable.ic_baseline_favorited_24)
        }

    }


}