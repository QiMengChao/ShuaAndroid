package com.qmc.shuaandroid.ui.fragment

import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.qmc.shuaandroid.adapter.FragmentAdapter
import com.qmc.shuaandroid.base.BaseFragment
import com.qmc.shuaandroid.databinding.FragmentNavigationBinding
import com.qmc.shuaandroid.model.ProjectViewModel

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/21-9:12
 *@Description
 */
class EventFragment:BaseFragment<ProjectViewModel,FragmentNavigationBinding>() {
    private var tagMap = mutableMapOf<String,Fragment>()

    override fun initView() {
        super.initView()
        vb.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
    }
    override fun initData() {
        vm.getProjectTags()
        vm.projectTag.observe(viewLifecycleOwner){
            it.forEach { innerIt ->
                tagMap[innerIt.name] = ProjectFragment.getInstance(innerIt.id)
            }
            vb.navVp.adapter = FragmentAdapter(childFragmentManager,tagMap.values.toList(),tagMap.keys.toList())
            vb.tabLayout.setupWithViewPager(vb.navVp)
        }
    }
}