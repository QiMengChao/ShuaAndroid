package com.qmc.shuaandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.just.agentweb.AgentWeb
import com.qmc.shuaandroid.adapter.NavigationAdapter
import com.qmc.shuaandroid.adapter.SystemAdapter
import com.qmc.shuaandroid.base.AGENT_LINK
import com.qmc.shuaandroid.base.BaseFragment
import com.qmc.shuaandroid.base.NAV_TYPE
import com.qmc.shuaandroid.databinding.RecyclerviewRefreshBinding
import com.qmc.shuaandroid.model.SystemViewModel
import com.qmc.shuaandroid.ui.activity.AgentWebActivity
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener


class SystemFragment: BaseFragment<SystemViewModel, RecyclerviewRefreshBinding>(),OnRefreshListener {

    private val navAdapter by lazy {
        NavigationAdapter(mutableListOf(), object : NavigationAdapter.FlexItemOnClick {
            override fun onFlexItemClick(link: String) {
                startActivity(Intent(context,AgentWebActivity::class.java).apply {
                    putExtra(AGENT_LINK,link)
                })
            }
        })
    }
    private val sysAdapter by lazy {
        SystemAdapter(mutableListOf())
    }

    companion object {
        /**
         *
         * @param type Int 类型，默认为1 请求导航接口 2为请求体系接口
         * @return Fragment
         */
        fun getInstance(type: Int = 1): Fragment {
            val systemFragment = SystemFragment()
            val bundle = Bundle()
            bundle.putInt(NAV_TYPE, type)
            systemFragment.arguments = bundle
            return systemFragment
        }
    }

    override fun initView() {
        super.initView()
        vb.smartRefresh.setOnRefreshListener(this)
        vb.smartRefresh.setEnableLoadMore(false)
        vb.rc.layoutManager = LinearLayoutManager(context)
}

    override fun initData() {
        vb.smartRefresh.autoRefresh()
        val type =  arguments?.getInt(NAV_TYPE, 1)
        if (type == 1) {
            vm.getNavigationData()
            vb.rc.adapter =navAdapter
        } else {
            vm.getSystemData()
            vb.rc.adapter = sysAdapter
        }
        vm.navigationData.observe(viewLifecycleOwner){
            navAdapter.setList(it.toMutableList())
            vb.smartRefresh.finishRefresh()
        }
        vm.systemData.observe(viewLifecycleOwner){
           sysAdapter.setList(it.toMutableList())
            vb.smartRefresh.finishRefresh()
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        initData()
    }


}