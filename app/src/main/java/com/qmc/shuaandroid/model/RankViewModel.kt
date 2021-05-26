package com.qmc.shuaandroid.model

import androidx.lifecycle.MutableLiveData
import com.qmc.network.model.RankModel
import com.qmc.shuaandroid.base.BaseViewModel
import com.qmc.shuaandroid.repository.IndexRepository

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/25-15:37
 *@Description
 */
class RankViewModel:BaseViewModel() {

    var rankData = MutableLiveData<RankModel>()

    fun getRankList(page:Int) {
        launch({IndexRepository.getRankList(page)},rankData)
    }
}