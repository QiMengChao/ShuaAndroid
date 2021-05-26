package com.qmc.shuaandroid.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ImmersionBar
import com.lxj.xpopup.XPopup
import com.qmc.shuaandroid.R
import java.lang.reflect.ParameterizedType

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/21-17:05
 *@Description
 */
abstract class BaseActivity<VM:BaseViewModel,VB:ViewBinding>:AppCompatActivity() {
    protected lateinit var vb:VB
    protected lateinit var vm:VM
    private val loadingPopup by lazy {
        XPopup.Builder(this)
            .asLoading(loadingContent())
    }
    protected val sp by lazy {
        SPUtils.getInstance()
    }
    protected var page = 0

    protected fun loadingContent(loadingContent:String = "加载中...") = loadingContent

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val parameterizedType = javaClass.genericSuperclass as ParameterizedType
        val clazz1 = parameterizedType.actualTypeArguments[0] as Class<VM>
        vm = ViewModelProviders.of(this).get(clazz1)

        val clazz2 = parameterizedType.actualTypeArguments[1] as Class<VB>
        val method = clazz2.getMethod("inflate",LayoutInflater::class.java)
        vb = method.invoke(null,layoutInflater) as VB
        setContentView(vb.root)
        initStatusBar()
        initDataStatus()
        initView()
        initData()
    }

    private fun initDataStatus() {

        vm.dialogLoading.observe(this) {
            if(it) {
                loadingPopup.show()
            } else {
                loadingPopup.dismiss()
            }
        }
        vm.dialogMessage.observe(this) {
            ToastUtils.showShort(it)
        }

    }

    protected fun initStatusBar(color:Int = R.color.white) {
        ImmersionBar.with(this)
            .statusBarColor(color)
            .navigationBarColor(color)
            .autoStatusBarDarkModeEnable(true)
            .fitsSystemWindows(true)
            .init()
    }

    open  fun initView() {}

    abstract fun initData()

}