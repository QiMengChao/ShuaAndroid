package com.qmc.shuaandroid.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.qmc.network.model.BannerModel
import com.qmc.shuaandroid.R
import com.qmc.shuaandroid.adapter.IndexAdapter
import com.qmc.shuaandroid.adapter.WanBannerAdapter
import com.qmc.shuaandroid.base.AGENT_LINK
import com.qmc.shuaandroid.base.BaseFragment
import com.qmc.shuaandroid.databinding.FragmentIndexBinding
import com.qmc.shuaandroid.model.IndexModel
import com.qmc.shuaandroid.ui.activity.AgentWebActivity
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.youth.banner.Banner
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.ScaleInTransformer


class IndexFragment:BaseFragment<IndexModel,FragmentIndexBinding>(),OnRefreshLoadMoreListener,OnItemClickListener,OnItemChildClickListener {
    private val indexAdapter by lazy {
        IndexAdapter(R.layout.adapter_official_item, listOf())
    }
    private val bannerHeader by lazy {
        LayoutInflater.from(activity).inflate(R.layout.banner,null)
    }

    private var likePosition: Int? = null



    override fun initView() {
        super.initView()
        val appCompatActivity = activity as AppCompatActivity
        // 必须加上不然不显示menu
        setHasOptionsMenu(true)
        appCompatActivity.setSupportActionBar(vb.mainToolbar.toolbar)
        appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
        if(indexAdapter.hasHeaderLayout()) indexAdapter.removeHeaderView(bannerHeader)
        indexAdapter.addHeaderView(bannerHeader)
        indexAdapter.addChildClickViewIds(R.id.likeFl)
        indexAdapter.setOnItemClickListener(this)
        indexAdapter.setOnItemChildClickListener(this)
    }

    override fun initData() {
        vb.smartRefresh.autoRefresh()
        vb.smartRefresh.setOnRefreshLoadMoreListener(this)
        vb.rc.adapter = indexAdapter
        vb.rc.layoutManager = LinearLayoutManager(context)
        vm.getArticle(page)
        vm.articleModelModel.observe(viewLifecycleOwner){
            indexAdapter.addData(it.datas)
            vb.smartRefresh.finishLoadMore()

        }
        vm.topArticleModelModel.observe(viewLifecycleOwner){
            vb.smartRefresh.finishRefresh()
            indexAdapter.setList(it)
        }
        vm.bannerModel.observe(viewLifecycleOwner){
            bannerHeader.findViewById<Banner<BannerModel,WanBannerAdapter<BannerModel>>>(R.id.banner).setAdapter(object : WanBannerAdapter<BannerModel>(it){
                override fun onBindView(
                    holder: BannerImageHolder?,
                    data: BannerModel?,
                    position: Int,
                    size: Int
                ) {
                    holder?.imageView?.load(data?.imagePath){
                        crossfade(true)
                        transformations(RoundedCornersTransformation(ConvertUtils.dp2px(10f).toFloat(),
                            ConvertUtils.dp2px(10f).toFloat(),ConvertUtils.dp2px(10f).toFloat(),
                            ConvertUtils.dp2px(10f).toFloat()))
                    }
                }
            }).addBannerLifecycleObserver(this)
                .addPageTransformer(ScaleInTransformer())
                .setIndicator(CircleIndicator(context));
        }

        vm.collectData.observe(this) {
            likePosition?.let { position ->
                indexAdapter.data[position].collect = !(indexAdapter.data[position].collect)
                indexAdapter.notifyItemChanged(position+1)
            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        vm.getArticle(page)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        vm.getArticle(page)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu,inflater)
        inflater.inflate(R.menu.titlebar_item, menu)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        startActivity(Intent(context, AgentWebActivity::class.java).apply {
            putExtra(AGENT_LINK,indexAdapter.data[position].link)
        })
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        likePosition = position
       val item = indexAdapter.data[position]
        if(item.collect) {
            vm.cancelCollect(item.id)
        } else {
            // 等于1站内文章
            if(item.type == 1) {
                vm.collectInner(item.id)
                // 等于0站外文章
            } else  {
                vm.collectOuter(item.title,item.author,item.link)
            }
        }

    }

}