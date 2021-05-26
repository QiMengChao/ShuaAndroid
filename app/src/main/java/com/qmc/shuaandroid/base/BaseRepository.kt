package com.qmc.shuaandroid.base

import com.qmc.network.base.RetrofitHelper
import com.qmc.network.service.WanAndroidService

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/18-9:37
 *@Description
 */
open class BaseRepository {
    val retrofitHelper = RetrofitHelper.createService(WanAndroidService::class.java)
}