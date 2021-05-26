package com.qmc.shuaandroid.ui.activity

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.qmc.shuaandroid.R
import com.qmc.shuaandroid.adapter.MineCollectAdapter
import com.qmc.shuaandroid.base.AGENT_LINK
import com.qmc.shuaandroid.base.BaseActivity
import com.qmc.shuaandroid.base.BaseViewModel
import com.qmc.shuaandroid.databinding.RecyclerviewTitleRefreshBinding
import com.qmc.shuaandroid.model.MineCollectViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/25-15:51
 *@Description
 */
class MineCollectActivity:BaseActivity<MineCollectViewModel,RecyclerviewTitleRefreshBinding>(),OnRefreshLoadMoreListener,OnItemClickListener,OnItemChildClickListener {

    private val mineCollectAdapter by lazy {
        MineCollectAdapter(mutableListOf())
    }

    private var selectPosition: Int? = null

    override fun initView() {
        super.initView()
        setSupportActionBar(vb.recyclerTitle.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        vb.recyclerTitle.toolbar.setNavigationOnClickListener {
            finish()
        }
        vb.recyclerTitle.titleTv.text = "我的收藏"
        vb.smartRefresh.setOnRefreshLoadMoreListener(this)
        vb.rc.layoutManager = LinearLayoutManager(this)
        vb.rc.adapter = mineCollectAdapter
        mineCollectAdapter.addChildClickViewIds(R.id.likeFl)
        mineCollectAdapter.setOnItemClickListener(this)
        mineCollectAdapter.setOnItemChildClickListener(this)
    }

    override fun initData() {
        vb.smartRefresh.autoRefresh()
        vm.getMineCollectList(page)
        vm.mineCollectData.observe(this){
            if(page == 0) {
                mineCollectAdapter.setList(it.datas)
              vb.smartRefresh.finishRefresh()
            } else {
                mineCollectAdapter.addData(it.datas)
                vb.smartRefresh.finishLoadMore()
            }
        }
        vm.cancelCollect.observe(this) {
            vb.smartRefresh.autoRefresh()
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        vm.getMineCollectList(page)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page = 1
        vm.getMineCollectList(page)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        startActivity(Intent(this, AgentWebActivity::class.java).apply {
            putExtra(AGENT_LINK,mineCollectAdapter.data[position].link)
        })
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        selectPosition = position
        vm.cancelCollect(mineCollectAdapter.data[position].id,mineCollectAdapter.data[position].originId)
    }
}