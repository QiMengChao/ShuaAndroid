package com.qmc.shuaandroid.model

import androidx.lifecycle.MutableLiveData
import com.qmc.network.model.LoginModel
import com.qmc.network.model.PersonAgeRankList
import com.qmc.network.model.IntegralModel
import com.qmc.shuaandroid.base.BaseViewModel
import com.qmc.shuaandroid.repository.IndexRepository

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/24-10:49
 *@Description
 */
class LoginViewModel:BaseViewModel() {
    var loginData = MutableLiveData<LoginModel>()
    var loginOut = MutableLiveData<Any>()
    var personAge = MutableLiveData<IntegralModel>()
    var personAgeIntegral = MutableLiveData<PersonAgeRankList>()

    fun login(account:String,pwd:String) {
        launchDialog({IndexRepository.login(account,pwd)},loginData)
    }
    fun loginOut() {
        launchDialog({IndexRepository.loginOut()},loginOut)
    }
    fun rank() {
        launch({IndexRepository.rank()},personAge)
    }

    fun personAgeIntegralList() {
        launchDialog({IndexRepository.personAgeIntegralList()},personAgeIntegral)
    }

}