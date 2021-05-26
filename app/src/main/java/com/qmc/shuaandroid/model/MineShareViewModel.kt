package com.qmc.shuaandroid.model

import androidx.lifecycle.MutableLiveData
import com.qmc.network.model.MineShareArticleModel
import com.qmc.shuaandroid.base.BaseViewModel
import com.qmc.shuaandroid.repository.IndexRepository

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/25-16:53
 *@Description
 */
class MineShareViewModel:BaseViewModel() {

    var mineShareData = MutableLiveData<MineShareArticleModel>()

    fun getMineShare(page:Int) {
        launch({IndexRepository.getMineShare(page)},mineShareData)
    }

}