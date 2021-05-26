package com.qmc.shuaandroid.ui.activity

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.tabs.TabLayout
import com.qmc.shuaandroid.R
import com.qmc.shuaandroid.base.*
import com.qmc.shuaandroid.databinding.ActivityLoginRegisterBinding
import com.qmc.shuaandroid.model.LoginViewModel
import org.greenrobot.eventbus.EventBus

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/21-17:19
 *@Description
 */
class LoginRegisterActivity:BaseActivity<LoginViewModel, ActivityLoginRegisterBinding>() {
    private val titles = listOf("登录", "注册")
    private var selectorPosition = 0

    override fun initView() {
        super.initView()
        vb.loginTab.addTab(vb.loginTab.newTab().setText(titles[0]),0)
        vb.loginTab.addTab(vb.loginTab.newTab().setText(titles[1]),1)
        vb.loginTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab?) {
                selectorPosition = p0?.position ?: 0
                if(p0?.position == 0) {
                    vb.login.text = "登录"
                }else  {
                    vb.login.text = "注册"
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {}

            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })
    }
    override fun initData() {
        vb.login.setOnClickListener {
            val account = vb.account.text.toString()
            val pwd = vb.pwd.text.toString()
            if(TextUtils.isEmpty(account)) {
                ToastUtils.showShort("账号不能为空")
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(pwd)) {
                ToastUtils.showShort("密码不能为空")
                return@setOnClickListener
            }
            if(selectorPosition ==0 ) {
                vm.login(account,pwd)
            }else {

            }
        }

        vm.loginData.observe(this) {
           sp.put(NICK_NAME,it.nickname)
           sp.put(PASS_WORD,vb.pwd.text.toString())
           sp.put(USER_ID,it.id)
            startActivity(Intent(this,MainActivity::class.java))
           finish()
        }

    }



}