package com.qmc.network.base

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/20-14:43
 *@Description
 */
class ApiException(val errorMsg:String,val code:Int):Throwable() {
}