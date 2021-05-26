package com.qmc.shuaandroid.model

import androidx.lifecycle.MutableLiveData
import com.qmc.network.model.EventModel
import com.qmc.network.model.ProjectModel
import com.qmc.shuaandroid.base.BaseViewModel
import com.qmc.shuaandroid.repository.IndexRepository

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/21-13:46
 *@Description
 */
class ProjectViewModel : BaseViewModel() {
    var projectTag = MutableLiveData<List<EventModel>>()
    var project = MutableLiveData<ProjectModel>()

    fun getProjectTags() {
        launch({ IndexRepository.getProjectTag() }, projectTag, true)
    }

    fun getProject(page: Int, cid: Int) {
        launch({IndexRepository.getProject(page,cid)},project)
    }

}