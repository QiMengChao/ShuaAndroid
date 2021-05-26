package com.qmc.shuaandroid.model

import androidx.lifecycle.MutableLiveData
import com.qmc.network.model.NavigationModel
import com.qmc.network.model.SystemModel
import com.qmc.shuaandroid.base.BaseViewModel
import com.qmc.shuaandroid.repository.IndexRepository

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/21-9:01
 *@Description
 */
class SystemViewModel: BaseViewModel() {
    var navigationData = MutableLiveData<List<NavigationModel>>()
    var systemData = MutableLiveData<List<SystemModel>>()

    fun getNavigationData() {
        launch({IndexRepository.getNavigation()},navigationData)
    }

    fun getSystemData() {
        launch({IndexRepository.getSystem()},systemData)
    }
}