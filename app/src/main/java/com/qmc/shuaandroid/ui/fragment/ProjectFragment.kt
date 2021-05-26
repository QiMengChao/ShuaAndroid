package com.qmc.shuaandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.qmc.shuaandroid.adapter.ProjectAdapter
import com.qmc.shuaandroid.base.AGENT_LINK
import com.qmc.shuaandroid.base.BaseFragment
import com.qmc.shuaandroid.base.PROJECT_TAG
import com.qmc.shuaandroid.databinding.RecyclerviewRefreshBinding
import com.qmc.shuaandroid.model.ProjectViewModel
import com.qmc.shuaandroid.ui.activity.AgentWebActivity
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/21-13:49
 *@Description
 */
class ProjectFragment : BaseFragment<ProjectViewModel, RecyclerviewRefreshBinding>(), OnRefreshLoadMoreListener ,OnItemClickListener{

    var cid: Int = 0
    private val adapter by lazy {
        ProjectAdapter(mutableListOf())
    }

    companion object {
        fun getInstance(cid: Int): ProjectFragment {
            val projectFragment = ProjectFragment()
            projectFragment.arguments = Bundle().apply {
                putInt(PROJECT_TAG, cid)
            }
            return projectFragment
        }
    }

    override fun initView() {
        super.initView()
        vb.rc.layoutManager = LinearLayoutManager(context)
        vb.rc.adapter = adapter
        adapter.setOnItemClickListener(this)
        vb.smartRefresh.setOnRefreshLoadMoreListener(this)
    }

    override fun initData() {
        cid = arguments?.getInt(PROJECT_TAG)!!
        vb.smartRefresh.autoRefresh()
        vm.getProject(page, cid)
        vm.project.observe(viewLifecycleOwner) {
            if(page == 0) {
                adapter.setList(it.datas.toMutableList())
                vb.smartRefresh.finishRefresh()
            }else {
                adapter.addData(it.datas.toMutableList())
                vb.smartRefresh.finishLoadMore()
            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        vm.getProject(page,cid)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        vm.getProject(page,cid)
    }

    override fun onItemClick(mAdapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

        startActivity(Intent(context,AgentWebActivity::class.java).apply {
            putExtra(AGENT_LINK,adapter.data[position].link)
        })
    }
}