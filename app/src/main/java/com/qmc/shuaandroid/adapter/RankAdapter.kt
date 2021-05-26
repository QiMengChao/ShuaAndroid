package com.qmc.shuaandroid.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qmc.network.model.InnerRank
import com.qmc.network.model.ItemIntegral
import com.qmc.shuaandroid.R

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/25-14:05
 *@Description  积分排行
 */
class RankAdapter(ranks:MutableList<InnerRank>):BaseQuickAdapter<InnerRank,BaseViewHolder>(R.layout.adapter_rank_item,ranks) {
    override fun convert(holder: BaseViewHolder, item: InnerRank) {
        holder.apply {
            setText(R.id.rankName,item.username)
            setText(R.id.rank,"第${item.rank}名")
            setText(R.id.rankIntegral,item.coinCount.toString().plus("积分"))
            setText(R.id.rankGrade,item.level.toString().plus("级"))
        }
    }
}