package com.qmc.shuaandroid.model

import androidx.lifecycle.MutableLiveData
import com.qmc.network.model.PersonAgeRankList
import com.qmc.shuaandroid.base.BaseViewModel
import com.qmc.shuaandroid.repository.IndexRepository

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/25-14:00
 *@Description
 */
class MineIntegralViewModel:BaseViewModel() {

    var integralData = MutableLiveData<PersonAgeRankList>()

    fun getMineIntegral(page:Int) {
        launch({IndexRepository.getMinIntegral(page)},integralData,true)
    }

}