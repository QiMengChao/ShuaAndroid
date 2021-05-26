package com.qmc.network.base

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/14-10:34
 *@Description 接口返回数据基类
 */
data class BaseModel<T>(val `data`:T,val errorCode:Int,val errorMsg:String)

