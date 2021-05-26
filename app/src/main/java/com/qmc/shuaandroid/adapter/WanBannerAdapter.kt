package com.qmc.shuaandroid.adapter

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.blankj.utilcode.util.ConvertUtils
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.holder.BannerImageHolder

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/19-13:51
 *@Description
 */
abstract class WanBannerAdapter<T>(list:List<T>) :BannerAdapter<T,BannerImageHolder>(list) {
    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerImageHolder {
       val imageView = ImageView(parent?.context)
       val vp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        imageView.layoutParams = vp
        imageView.setPadding(ConvertUtils.dp2px(15f),0,ConvertUtils.dp2px(15f),0)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerImageHolder(imageView)
    }

}