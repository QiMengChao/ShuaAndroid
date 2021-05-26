package com.qmc.shuaandroid.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.qmc.shuaandroid.adapter.MineIntegralRecordAdapter
import com.qmc.shuaandroid.base.BaseActivity
import com.qmc.shuaandroid.base.BaseViewModel
import com.qmc.shuaandroid.databinding.RecyclerviewTitleRefreshBinding
import com.qmc.shuaandroid.model.MineIntegralViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/25-13:44
 *@Description
 */
class MineIntegralActivity : BaseActivity<MineIntegralViewModel,RecyclerviewTitleRefreshBinding>(),OnRefreshLoadMoreListener{

   private  val integralAdapter by lazy {
        MineIntegralRecordAdapter(mutableListOf())
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
        vb.recyclerTitle.titleTv.text = "我的积分"
        vb.smartRefresh.setOnRefreshLoadMoreListener(this)
        vb.rc.layoutManager = LinearLayoutManager(this)
        vb.rc.adapter = integralAdapter
    }

    override fun initData() {
        // 自动刷新
        vb.smartRefresh.autoRefresh()
        vm.getMineIntegral(page)
        vm.integralData.observe(this) {
            if(page == 0){
                integralAdapter.setList(it.datas)
                vb.smartRefresh.finishRefresh()
            }  else {
                integralAdapter.addData(it.datas)
                vb.smartRefresh.finishLoadMore()
            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        vm.getMineIntegral(page)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        vm.getMineIntegral(page)
    }
}