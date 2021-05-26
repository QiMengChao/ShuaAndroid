package com.qmc.shuaandroid.ui.activity

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.qmc.shuaandroid.R
import com.qmc.shuaandroid.adapter.MineShareAdapter
import com.qmc.shuaandroid.base.AGENT_LINK
import com.qmc.shuaandroid.base.BaseActivity
import com.qmc.shuaandroid.base.BaseViewModel
import com.qmc.shuaandroid.databinding.RecyclerviewTitleRefreshBinding
import com.qmc.shuaandroid.model.MineShareViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/25-16:49
 *@Description
 */
class MineShareActivity:BaseActivity<MineShareViewModel,RecyclerviewTitleRefreshBinding>(),OnRefreshLoadMoreListener,OnItemClickListener {

    private val mineShareAdapter by lazy {
        MineShareAdapter(mutableListOf())
    }

    override fun initView() {
        super.initView()
        setSupportActionBar(vb.recyclerTitle.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        vb.recyclerTitle.toolbar.setNavigationOnClickListener {
            finish()
        }
        vb.recyclerTitle.titleTv.text = "我的分享"
        vb.smartRefresh.setOnRefreshLoadMoreListener(this)
        vb.rc.layoutManager = LinearLayoutManager(this)
        vb.rc.adapter = mineShareAdapter
        mineShareAdapter.setOnItemClickListener(this)
    }

    override fun initData() {
        vb.smartRefresh.autoRefresh()
        vm.getMineShare(page)
        vm.mineShareData.observe(this) {
            if( page == 0) {
                mineShareAdapter.setList(it.shareArticles.datas)
                vb.smartRefresh.finishRefresh()
            }else {
                mineShareAdapter.addData(it.shareArticles.datas)
                vb.smartRefresh.finishLoadMore()
            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        vm.getMineShare(page)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        vm.getMineShare(page)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        startActivity(Intent(this, AgentWebActivity::class.java).apply {
            putExtra(AGENT_LINK,mineShareAdapter.data[position].link)
        })
    }
}