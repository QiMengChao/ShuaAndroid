package com.qmc.shuaandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/13-15:47
 *@Description
 */
class FragmentAdapter(
    fm: FragmentManager,
    private val fragments: List<Fragment>,
    private var titles: List<String>,
):FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount() = titles.size

    override fun getItem(position: Int) = fragments[position]


    override fun getPageTitle(position: Int) = titles[position]
}