package com.qmc.shuaandroid.adapter

import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.FlexboxLayout
import com.just.agentweb.AgentWeb
import com.qmc.network.model.SystemModel
import com.qmc.shuaandroid.R


/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/21-10:48
 *@Description
 */
class SystemAdapter(var sysList: MutableList<SystemModel>):BaseQuickAdapter<SystemModel, BaseViewHolder>(
    R.layout.adapter_nav_item,
    sysList
) {
    override fun convert(holder: BaseViewHolder, item: SystemModel) {
        holder.setText(R.id.navItemTitle, item.name)
        val flexNav =  holder.getView<FlexboxLayout>(R.id.flexNav)
        for(children in item.children){
          val flexItem =  LayoutInflater.from(context).inflate(
              R.layout.flex_textview,
              flexNav,
              false
          )
            flexItem.findViewById<TextView>(R.id.flexItem).text = children.name
            flexNav.addView(flexItem)
        }

    }
    interface FlexItemOnClick {
        fun onFlexItemClick(link:String)
    }
}