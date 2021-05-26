package com.qmc.network.model

data class PersonAgeRankList(
    val curPage: Int,
    val datas: List<ItemIntegral>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class ItemIntegral(
    val coinCount: Int,
    val date: Long,
    val desc: String,
    val id: Int,
    val reason: String,
    val type: Int,
    val userId: Int,
    val userName: String
)