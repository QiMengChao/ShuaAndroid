package com.qmc.shuaandroid.adapter

import android.widget.ImageView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qmc.network.model.ProjectItem
import com.qmc.shuaandroid.R

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/21-14:40
 *@Description
 */
class ProjectAdapter(projects: MutableList<ProjectItem>) :
    BaseQuickAdapter<ProjectItem, BaseViewHolder>(R.layout.adapter_project_item, projects) {
    override fun convert(holder: BaseViewHolder, item: ProjectItem) {
        holder.setText(R.id.projectTag, item.chapterName)
        holder.getView<ImageView>(R.id.projectImg).load(item.envelopePic) {
            transformations(
                RoundedCornersTransformation(
                    ConvertUtils.dp2px(6f).toFloat(),
                    ConvertUtils.dp2px(6f).toFloat(),
                    ConvertUtils.dp2px(6f).toFloat(),
                    ConvertUtils.dp2px(6f).toFloat()
                )
            )
        }
        holder.setText(R.id.projectDate, item.publishTime.toString())
        holder.setText(R.id.projectAuthor, item.author)
        holder.setText(R.id.projectTitle, item.title)
    }
}