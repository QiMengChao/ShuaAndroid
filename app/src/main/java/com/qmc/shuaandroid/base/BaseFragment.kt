package com.qmc.shuaandroid.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStores.of
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ImmersionBar
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.lxj.xpopup.XPopup
import com.qmc.shuaandroid.R
import com.qmc.shuaandroid.status.EmptyCallBack
import com.qmc.shuaandroid.status.ErrorCallBack
import com.qmc.shuaandroid.status.LoadingCallBack
import java.lang.reflect.ParameterizedType
import java.util.EnumSet.of

/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/18-11:22
 *@Description
 */

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding> : Fragment() {
    protected lateinit var vb: VB
    protected lateinit var vm: VM
    protected lateinit var loadService: LoadService<Any>
    protected var page = 0
    protected val sp by lazy {
        SPUtils.getInstance()
    }
    private val loadingPopup by lazy {
        XPopup.Builder(context)
            .asLoading(loadingContent())
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz1 = type.actualTypeArguments[0] as Class<VM>
        vm = ViewModelProviders.of(this).get(clazz1)

        val clazz2 = type.actualTypeArguments[1] as Class<VB>
        val method = clazz2.getMethod("inflate", LayoutInflater::class.java)
        vb = method.invoke(clazz2, layoutInflater) as VB
        loadService = LoadSir.getDefault().register(
            vb.root
        ) {
            // 不管怎样重新将页数置为0
            page = 0
            initData()
        }
        return loadService.loadLayout
    }

    protected fun loadingContent(loadingContent:String = "加载中...") = loadingContent

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initStatusBar()
        initDataStatus()
        initView()
        initData()
    }

    open fun initView() {
        Log.e("initView", "initView: ")
    }

    protected fun initStatusBar(color:Int = R.color.white) {
        ImmersionBar.with(this)
            .statusBarColor(color)
            .navigationBarColor(color)
            .autoStatusBarDarkModeEnable(true)
            .fitsSystemWindows(true)
            .init()
    }


    private fun initDataStatus() {
        vm.error.observe(viewLifecycleOwner){
            loadService.showCallback(ErrorCallBack::class.java)
            loadService.loadLayout.findViewById<TextView>(R.id.errorTv).text = it
        }
        vm.loading.observe(viewLifecycleOwner){
            if(it) loadService.showCallback(LoadingCallBack::class.java)
        }
        vm.empty.observe(viewLifecycleOwner){
            if(it) loadService.showCallback(EmptyCallBack::class.java)
        }
        vm.complete.observe(viewLifecycleOwner){
            if(it) loadService.showSuccess()
        }
        vm.dialogMessage.observe(viewLifecycleOwner) {
            ToastUtils.showShort(it)
        }
        vm.dialogLoading.observe(viewLifecycleOwner){
            if(it) loadingPopup.show() else loadingPopup.dismiss()

        }
    }


    abstract fun initData()

}