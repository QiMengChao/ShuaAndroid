package com.qmc.shuaandroid.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qmc.network.model.ItemIntegral
import com.qmc.shuaandroid.R

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/25-14:05
 *@Description  个人积分明细适配器
 */
class MineIntegralRecordAdapter(integralRecords:MutableList<ItemIntegral>):BaseQuickAdapter<ItemIntegral,BaseViewHolder>(R.layout.adapter_integral_item,integralRecords) {
    override fun convert(holder: BaseViewHolder, item: ItemIntegral) {
        holder.apply {
            setText(R.id.integralType,item.reason)
            setText(R.id.integralDesc,item.desc)
            setText(R.id.integralTotal,item.coinCount.toString())
        }
    }
}