package com.qmc.network.base

import android.content.ContentValues.TAG
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.util.Log.VERBOSE
import com.blankj.utilcode.util.SPUtils
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.qmc.network.service.WanAndroidService
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.collections.MutableMap as MutableMap1

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/14-10:42
 *@Description
 */

object RetrofitHelper {
    private const val BASE_URL = "https://www.wanandroid.com/"
    private const val CONNECT_TIME = 10L
    private const val READ_TIME = 10L

    private val okHttpClient = OkHttpClient().newBuilder().apply {
        addInterceptor(
            LoggingInterceptor.Builder()
                .setLevel(Level.BASIC)
                .log(Log.ERROR)
                .build()
        )
            .addInterceptor(Interceptor { chain ->
               val newRequest = chain.request().newBuilder()
                   .addHeader("Cookie","loginUserName=${SPUtils.getInstance().getString("NICK_NAME")}")
                   .addHeader("Cookie","loginUserPassword=${SPUtils.getInstance().getString("PASS_WORD")}")
                chain.proceed(newRequest.build())

            })

        connectTimeout(CONNECT_TIME, TimeUnit.SECONDS)
        readTimeout(READ_TIME, TimeUnit.SECONDS)
    }
        .build()


    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun <T> createService(serviceClass: Class<T>): T = retrofit.create(serviceClass)
}