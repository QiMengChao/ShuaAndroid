package com.qmc.shuaandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.qmc.shuaandroid.base.BaseActivity
import com.qmc.shuaandroid.base.BaseViewModel
import com.qmc.shuaandroid.base.LOG_TAG
import com.qmc.shuaandroid.base.NICK_NAME
import com.qmc.shuaandroid.databinding.ActivitySplashBinding

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/21-16:56
 *@Description
 */
class SplashActivity : BaseActivity<BaseViewModel, ActivitySplashBinding>() {
    private val countDownTimer =object: CountDownTimer(3000,1000){
        override fun onTick(millisUntilFinished: Long) {
            vb.countDown.text = millisUntilFinished.toInt().div(1000) .toString().plus("秒")
        }


        override fun onFinish() {
            skip()
        }

    }

    private fun skip() {
        if (TextUtils.isEmpty(sp.getString(NICK_NAME))) {
            startActivity(Intent(this@SplashActivity, LoginRegisterActivity::class.java))
        } else {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }
        finish()
    }

    override fun initView() {
        vb.countDown.setOnClickListener {
            countDownTimer.cancel()
            skip()
        }
    }

    override fun initData() {
        Log.e(LOG_TAG, "initData: ")
        countDownTimer.start()
    }

    override fun onPause() {
        super.onPause()
        countDownTimer.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

    override fun onBackPressed() {

    }



}