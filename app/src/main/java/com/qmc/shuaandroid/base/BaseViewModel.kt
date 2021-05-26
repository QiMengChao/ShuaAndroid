package com.qmc.shuaandroid.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.qmc.network.base.ApiException
import com.qmc.network.base.BaseModel
import kotlinx.coroutines.*
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/18-9:18
 *@Description
 */
open class BaseViewModel : ViewModel() {

    var error = MutableLiveData<String>()

    var loading = MutableLiveData<Boolean>()

    var empty = MutableLiveData<Boolean>()

    var complete = MutableLiveData<Boolean>()

    var dialogLoading = MutableLiveData<Boolean>()

    var dialogMessage = MutableLiveData<String>()

    fun <T> launch(
        block: suspend CoroutineScope.() -> BaseModel<T>,
        liveData: MutableLiveData<T>,
        globalLoading:Boolean = false
    ) {
        viewModelScope.launch {
           if(globalLoading) loading.postValue(true)
            try {
                val result = withContext(Dispatchers.IO) { block() }
                if (result.errorCode == 0) {
                    liveData.value = result.data
                    complete.value = true
                } else {
                    error.value = result.errorMsg
                }
            } catch (e: Exception) {
                error.postValue(getApiException(e).errorMsg)
            } finally {
                dialogLoading.postValue(false)
            }
        }
    }

    fun <T> launchDialog(
        block: suspend CoroutineScope.() -> BaseModel<T>,
        liveData: MutableLiveData<T>
    ) {
        viewModelScope.launch {
            dialogLoading.postValue(true)
            try {
                val result = withContext(Dispatchers.IO) { block() }
                Log.e(LOG_TAG, "launchDialog: $result")
                if (result.errorCode == 0) {
                    liveData.value = result.data
                } else {
                    dialogMessage.value = result.errorMsg
                }
            } catch (e: Exception) {
                dialogLoading.postValue(false)
                dialogMessage.value = getApiException(e).errorMsg
            } finally {
                dialogLoading.postValue(false)
            }
        }
    }

    /**
     * 捕获异常信息
     */
    private fun getApiException(e: Throwable): ApiException {
        return when (e) {
            is UnknownHostException -> {
                ApiException("网络异常", -100)
            }
            is JSONException -> {//|| e is JsonParseException
                ApiException("数据异常", -100)
            }
            is SocketTimeoutException -> {
                ApiException("连接超时", -100)
            }
            is ConnectException -> {
                ApiException("连接错误", -100)
            }
            is HttpException -> {
                ApiException("http code ${e.response.code}", -100)
            }
            is ApiException -> {
                e
            }
            /**
             * 如果协程还在运行，个别机型退出当前界面时，viewModel会通过抛出CancellationException，
             * 强行结束协程，与java中InterruptException类似，所以不必理会,只需将toast隐藏即可
             */
            is CancellationException -> {
                ApiException("", -10)
            }
            else -> {
                ApiException("未知错误", -100)
            }
        }
    }

}