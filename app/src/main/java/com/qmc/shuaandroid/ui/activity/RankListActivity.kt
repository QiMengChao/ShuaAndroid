package com.qmc.shuaandroid.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.qmc.shuaandroid.adapter.RankAdapter
import com.qmc.shuaandroid.base.BaseActivity
import com.qmc.shuaandroid.base.BaseViewModel
import com.qmc.shuaandroid.databinding.RecyclerviewTitleRefreshBinding
import com.qmc.shuaandroid.model.RankViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/25-15:21
 *@Description
 */
class RankListActivity:BaseActivity<RankViewModel,RecyclerviewTitleRefreshBinding>(),OnRefreshLoadMoreListener {

    private val rankAdapter by lazy {
        RankAdapter(mutableListOf())
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
        vb.recyclerTitle.titleTv.text = "积分排行"
        vb.smartRefresh.setOnRefreshLoadMoreListener(this)
        vb.rc.layoutManager = LinearLayoutManager(this)
        vb.rc.adapter = rankAdapter
    }

    override fun initData() {
        vb.smartRefresh.autoRefresh()
        vm.getRankList(page = 1)
        vm.rankData.observe(this) {
            if(page == 1) {
                rankAdapter.setList(it.datas)
                vb.smartRefresh.finishRefresh()
            } else  {
                rankAdapter.addData(it.datas)
                vb.smartRefresh.finishLoadMore()
            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 1
        vm.getRankList(page)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        vm.getRankList(page)
    }
}