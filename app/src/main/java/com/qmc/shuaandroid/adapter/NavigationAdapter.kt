package com.qmc.shuaandroid.adapter

import android.view.LayoutInflater
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.FlexboxLayout
import com.qmc.network.model.NavigationModel
import com.qmc.shuaandroid.R

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/21-10:48
 *@Description
 */
class NavigationAdapter(navList:MutableList<NavigationModel>,var flexItemOnClick: FlexItemOnClick):BaseQuickAdapter<NavigationModel,BaseViewHolder>(R.layout.adapter_nav_item,navList) {


    override fun convert(holder: BaseViewHolder, item: NavigationModel) {
        holder.setText(R.id.navItemTitle,item.name)
        val flexNav =  holder.getView<FlexboxLayout>(R.id.flexNav)
        for(children in item.articles){
            val flexItem =  LayoutInflater.from(context).inflate(R.layout.flex_textview,flexNav,false)
            flexItem.findViewById<TextView>(R.id.flexItem).text = children.title
            flexItem.setOnClickListener {
                flexItemOnClick.onFlexItemClick(children.link)
            }
            flexNav.addView(flexItem)
        }
    }
    interface FlexItemOnClick {
        fun onFlexItemClick(link:String)
    }
}