package com.qmc.shuaandroid.repository

import com.qmc.shuaandroid.base.BaseRepository

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/18-9:33
 *@Description
 */
object IndexRepository : BaseRepository() {


    suspend fun getArticles(page: Int = 0) = retrofitHelper.getArticles(page)

    suspend fun getBanner() = retrofitHelper.getBanner()

    suspend fun getTopArticle() = retrofitHelper.getTopArticles()

    suspend fun getNavigation() = retrofitHelper.getNavigation()

    suspend fun getSystem() = retrofitHelper.getSystem()

    suspend fun getOfficial() = retrofitHelper.getOfficialAccountList()

    suspend fun getProjectTag() = retrofitHelper.getProjectTag()

    suspend fun getProject(page: Int, cid: Int) = retrofitHelper.getProjects(page,cid)

    suspend fun login(username:String,pwd:String) = retrofitHelper.login(username,pwd)

    suspend fun loginOut() = retrofitHelper.loginOut()

    suspend fun rank() = retrofitHelper.personageRank()

    suspend fun personAgeIntegralList() = retrofitHelper.getIntegralList()

    suspend fun getMinIntegral(page: Int) = retrofitHelper.getIntegralList(page)

    suspend fun collectInner(id:Int) =  retrofitHelper.collectInner(id)

    suspend fun collectOuter(title:String,author:String,link:String) = retrofitHelper.collectOuter(title,author, link)

    suspend fun cancelCollect(id:Int) = retrofitHelper.cancelCollect(id)

    suspend fun getRankList(page:Int) = retrofitHelper.rankList(page)

    suspend fun getMineCollect(page:Int) = retrofitHelper.mineCollect(page)

    suspend fun cancelMineCollect(id:Int,originId:Int) = retrofitHelper.cancelMineCollect(id,originId)

    suspend fun getMineShare(page:Int) = retrofitHelper.mineShareArticle(page)
}