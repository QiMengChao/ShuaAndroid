package com.qmc.network.model

data class RankModel(
    val curPage: Int,
    val datas: List<InnerRank>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)
data class InnerRank(
    val coinCount: Int,
    val level: Int,
    val nickname: String,
    val rank: String,
    val userId: Int,
    val username: String
)