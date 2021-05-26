package com.qmc.shuaandroid.base

import android.app.Application
import android.content.Context
import android.hardware.Camera.ErrorCallback
import com.kingja.loadsir.callback.SuccessCallback

import com.kingja.loadsir.core.LoadSir
import com.qmc.shuaandroid.status.EmptyCallBack
import com.qmc.shuaandroid.status.ErrorCallBack
import com.qmc.shuaandroid.status.LoadingCallBack
import com.qmc.shuaandroid.status.NetWorkCallBack
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator


/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/18-9:52
 *@Description
 */
class ShuaApp:Application() {



    companion object {
        lateinit var context: Context

        // kotlin中的静态代码块
        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ -> MaterialHeader(context) }
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ -> BallPulseFooter(context) }
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        LoadSir.beginBuilder()
            .addCallback(ErrorCallBack()) //添加各种状态页
            .addCallback(EmptyCallBack())
            .addCallback(LoadingCallBack())
            .addCallback(NetWorkCallBack())
            .setDefaultCallback(SuccessCallback::class.java)
            .commit()
    }

}