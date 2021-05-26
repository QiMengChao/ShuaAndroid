package com.qmc.shuaandroid.ui.activity

import android.os.Bundle
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.gyf.immersionbar.ImmersionBar
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.qmc.shuaandroid.R
import com.qmc.shuaandroid.base.AGENT_LINK
import com.qmc.shuaandroid.databinding.ActivityAgentWebBinding

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/21-12:31
 *@Description
 */
class AgentWebActivity:AppCompatActivity() {
    private val agentWebActivity by lazy { ActivityAgentWebBinding.inflate(layoutInflater) }
    private lateinit var agentWeb: AgentWeb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(agentWebActivity.root)
        initData()
    }

    private fun initData() {
        ImmersionBar.with(this)
            .statusBarColor(R.color.white)
            .navigationBarColor(R.color.white)
            .autoStatusBarDarkModeEnable(true)
            .fitsSystemWindows(true)
            .init()
        setSupportActionBar(agentWebActivity.agentToolbar.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        agentWebActivity.agentToolbar.toolbar.setNavigationOnClickListener {
            finish()
        }
       val link = intent.getStringExtra(AGENT_LINK)
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(agentWebActivity.agentCs, ConstraintLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setWebChromeClient(mWebChromeClient)
            .setWebViewClient(mWebViewClient)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK) //打开其他应用时，弹窗咨询用户是否前往其他应用
            .interceptUnkownUrl() //拦截找不到相关页面的Scheme
            .createAgentWeb()
            .ready()
            .go(link)
    }

    private val mWebChromeClient = object:WebChromeClient(){
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            agentWebActivity.agentToolbar.toolbar.findViewById<TextView>(R.id.titleTv).text = title
        }
    }
    private val mWebViewClient = object :WebViewClient(){

    }

    override fun onResume() {
        agentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onPause() {
        agentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        agentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }
}