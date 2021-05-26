package com.qmc.shuaandroid.ui.fragment

import com.qmc.shuaandroid.adapter.FragmentAdapter
import com.qmc.shuaandroid.base.BaseFragment
import com.qmc.shuaandroid.base.BaseViewModel
import com.qmc.shuaandroid.databinding.FragmentNavigationBinding

class NavigationFragment:BaseFragment<BaseViewModel,FragmentNavigationBinding>() {

    private val fragments = mapOf("导航" to SystemFragment.getInstance(1),"体系" to SystemFragment.getInstance(2))

    override fun initData() {
        loadService.showSuccess()
        vb.navVp.adapter = FragmentAdapter(childFragmentManager,fragments.values.toList(),fragments.keys.toList())
        vb.tabLayout.setupWithViewPager(vb.navVp)
    }


}