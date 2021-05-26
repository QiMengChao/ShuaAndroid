package com.qmc.shuaandroid.model

import androidx.lifecycle.MutableLiveData
import com.qmc.network.model.MineCollectModel
import com.qmc.shuaandroid.base.BaseViewModel
import com.qmc.shuaandroid.repository.IndexRepository

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/25-16:02
 *@Description
 */
class MineCollectViewModel:BaseViewModel() {

    var mineCollectData = MutableLiveData<MineCollectModel>()
    var cancelCollect = MutableLiveData<Any>()

    fun getMineCollectList(page:Int) {
        launch({IndexRepository.getMineCollect(page)},mineCollectData)
    }
    fun cancelCollect(id:Int,originId:Int) {
        launchDialog({IndexRepository.cancelMineCollect(id,originId)},cancelCollect)
    }

}