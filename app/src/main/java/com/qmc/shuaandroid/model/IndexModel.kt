package com.qmc.shuaandroid.model

import androidx.lifecycle.MutableLiveData
import com.qmc.network.model.ArticleModel
import com.qmc.network.model.BannerModel
import com.qmc.network.model.InnerData
import com.qmc.shuaandroid.base.BaseViewModel
import com.qmc.shuaandroid.repository.IndexRepository

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/18-9:23
 *@Description
 */

class IndexModel : BaseViewModel() {

    var topArticleModelModel = MutableLiveData<List<InnerData>>()
    var bannerModel = MutableLiveData<List<BannerModel>>()
    var articleModelModel = MutableLiveData<ArticleModel>()
    var collectData = MutableLiveData<Any>()

    fun getArticle(page: Int = 0) {
        if (page == 0) {
            launch({ IndexRepository.getTopArticle() }, topArticleModelModel)
            launch({ IndexRepository.getBanner() }, bannerModel)
        } else  {
            launch({ IndexRepository.getArticles(page)},articleModelModel)
        }
    }


    fun collectInner(id: Int) {
        launchDialog({IndexRepository.collectInner(id)},collectData)
    }

    fun collectOuter(title: String, author: String, link: String) {
        launchDialog({IndexRepository.collectOuter(title, author, link)},collectData)
    }

    fun cancelCollect(id: Int) {
        launchDialog({IndexRepository.cancelCollect(id)},collectData)
    }
}