package com.qmc.network.service

import com.qmc.network.base.BaseModel
import com.qmc.network.model.*
import retrofit2.http.*

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/14-10:29
 *@Description 接口
 */
interface WanAndroidService {
    // 获取公众号列表
    @GET("wxarticle/chapters/json")
    suspend fun getOfficialAccountList(): BaseModel<List<OfficialAccountModel>>

    // 获取轮播图
    @GET("banner/json")
    suspend fun getBanner(): BaseModel<List<BannerModel>>

    // 文章列表
    @GET("article/list/{page}/json")
    suspend fun getArticles(@Path("page") page: Int = 0): BaseModel<ArticleModel>

    // 置顶文章
    @GET("article/top/json")
    suspend fun getTopArticles(): BaseModel<List<InnerData>>

    // 获取导航结构
    @GET("navi/json")
    suspend fun getNavigation(): BaseModel<List<NavigationModel>>

    // 获取体系结构
    @GET("tree/json")
    suspend fun getSystem(): BaseModel<List<SystemModel>>

    // 获取项目标签
    @GET("project/tree/json")
    suspend fun getProjectTag(): BaseModel<List<EventModel>>

    // 获取项目标签下的项目
    @GET("project/list/{page}/json")
    suspend fun getProjects(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): BaseModel<ProjectModel>

    // 登录
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): BaseModel<LoginModel>

    // 退出登录
    @GET("user/logout/json")
    suspend fun loginOut(): BaseModel<Any>

    // 个人积分
    @GET("lg/coin/userinfo/json")
    suspend fun personageRank(): BaseModel<IntegralModel>

    // 获取个人积分列表
    @GET("lg/coin/list/{page}/json")
    suspend fun getIntegralList(@Path("page") page: Int = 0): BaseModel<PersonAgeRankList>

    // 收藏站内文章
    @POST("lg/collect/{id}/json")
    suspend fun collectInner(@Path("id") id: Int): BaseModel<Any>

    // 收藏站外文章
    @POST("lg/collect/add/json")
    suspend fun collectOuter(
        @Field("title") title: String,
        @Field("author") author: String,
        @Field("link") link: String
    ): BaseModel<Any>

    // 取消收藏
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun cancelCollect(@Path("id") id: Int): BaseModel<Any>

    // 积分排行帮
    @GET("coin/rank/{page}/json")
    suspend fun rankList(@Path("page") page: Int = 1): BaseModel<RankModel>

    // 我的收藏
    @GET("lg/collect/list/{page}/json")
    suspend fun mineCollect(@Path("page") page: Int = 0): BaseModel<MineCollectModel>

    // 取消我的收藏
    @POST("lg/uncollect/{id}/json")
    suspend fun cancelMineCollect(@Path("id") id: Int,@Query("originId") originId:Int = -1): BaseModel<Any>

    // 我的分享的文章
    @GET("user/lg/private_articles/{page}/json")
    suspend fun mineShareArticle(@Path("page") page:Int):BaseModel<MineShareArticleModel>

}