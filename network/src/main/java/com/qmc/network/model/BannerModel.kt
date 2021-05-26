package com.qmc.network.model

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/18-15:44
 *@Description
 */
data class BannerModel(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)