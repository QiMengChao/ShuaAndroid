package com.qmc.shuaandroid.ui.fragment

import android.content.Intent
import android.text.TextUtils
import android.view.View
import coil.load
import coil.transform.CircleCropTransformation
import com.blankj.utilcode.util.ToastUtils
import com.lxj.xpopup.XPopup
import com.qmc.shuaandroid.R
import com.qmc.shuaandroid.base.*
import com.qmc.shuaandroid.databinding.FragmentMineBinding
import com.qmc.shuaandroid.model.LoginViewModel
import com.qmc.shuaandroid.ui.activity.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pub.devrel.easypermissions.EasyPermissions


/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/13-15:39
 *@Description
 */
class MineFragment : BaseFragment<LoginViewModel, FragmentMineBinding>(),
    EasyPermissions.PermissionCallbacks {

    override fun initView() {
        super.initView()
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
        vb.cir.setOnClickListener {
            if (!TextUtils.isEmpty(sp.getString(NICK_NAME))) {
                startActivity(Intent(context, LoginRegisterActivity::class.java))
            }
        }
        vb.nickName.setOnClickListener {
            if (!TextUtils.isEmpty(sp.getString(NICK_NAME))) {
                startActivity(Intent(context, LoginRegisterActivity::class.java))
            }
        }
        vb.mineExit.setOnClickListener {
            XPopup.Builder(context).asConfirm(
                "退出", "是否退出登录？"
            ) { vm.loginOut() }
                .show()
        }
        if (!TextUtils.isEmpty(sp.getString(NICK_NAME))) {
            vb.nickName.text = sp.getString(NICK_NAME)
            vb.userId.text = sp.getInt(USER_ID, 0).toString()
        }

        vb.mineIntegral.setOnClickListener {
            onViewClick(it)
        }
        vb.mineRank.setOnClickListener {
            onViewClick(it)
        }
        vb.mineCollect.setOnClickListener {
            onViewClick(it)
        }
        vb.mineShare.setOnClickListener {
            onViewClick(it)
        }
        vb.cir.setOnClickListener {
            onViewClick(it)
        }
    }

    private fun onViewClick(view: View) {
        if (TextUtils.isEmpty(sp.getString(NICK_NAME))) {
            ToastUtils.showShort("请先登录")
            return
        }
        when (view.id) {
            R.id.mineIntegral -> {
                startActivity(Intent(context, MineIntegralActivity::class.java))
            }
            R.id.mineRank -> {
                startActivity(Intent(context, RankListActivity::class.java))
            }
            R.id.mineCollect -> startActivity(Intent(context, MineCollectActivity::class.java))
            R.id.mineShare -> startActivity(Intent(context, MineShareActivity::class.java))
            R.id.cir -> {
                XPopup.Builder(context)
                    .asBottomList(
                        "设置头像", arrayOf("相册", "拍照")
                    ) { position, text ->

                        if (position == 0) {
                            startActivityForResult(
                                Intent(
                                    Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                ), PICTURE_REQUEST_CODE
                            );
                        }

                    }
                    .show()
            }
        }
    }

    override fun initData() {
        loadService.showSuccess()
        // 获取积分
        if (!TextUtils.isEmpty(sp.getString(NICK_NAME))) {
            vm.rank()
        }
        vm.loginOut.observe(viewLifecycleOwner) {
            sp.remove(NICK_NAME)
            sp.remove(PASS_WORD)
            sp.remove(USER_ID)
            startActivity(Intent(context, LoginRegisterActivity::class.java))
            activity?.finish()
        }
        vm.personAge.observe(viewLifecycleOwner) {
            vb.integral.text = it.coinCount.toString()
            vb.rank.text = it.rank
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updateLogin(loginCode: String) {
        if (loginCode == LOGIN_SUCCESS_CODE) {
            val nickName = sp.getString(NICK_NAME)
            if (!TextUtils.isEmpty(nickName)) {
                vb.nickName.text = nickName
                vb.userId.text = sp.getInt(USER_ID, 0).toString()
                vm.rank()
            } else {
                vb.nickName.text = "点击登录"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        ToastUtils.showShort("权限拒绝，请前往设置中心开启")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        vb.cir.load(data?.data) {
            transformations(CircleCropTransformation())
        }
    }

}