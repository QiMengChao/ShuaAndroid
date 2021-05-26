package com.qmc.shuaandroid.ui.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import androidx.core.content.FileProvider
import androidx.core.os.EnvironmentCompat
import coil.load
import coil.transform.CircleCropTransformation
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
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
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


/**
 *Project_Name ShuaAndroid
 *@Author qimengchao
 *@Date 2021/5/13-15:39
 *@Description
 */
class MineFragment : BaseFragment<LoginViewModel, FragmentMineBinding>(),
    EasyPermissions.PermissionCallbacks {
    // 是否是Android 10以上手机
    private val isAndroidQ = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    // 保存图片的Uri
    private var mCamera: Uri? = null
    private var cameraPath: String? = null

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
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                ), PICTURE_REQUEST_CODE
                            )
                        } else {
                            if (!EasyPermissions.hasPermissions(
                                    requireContext(),
                                    Manifest.permission.CAMERA
                                )
                            ) {
                                EasyPermissions.requestPermissions(
                                    this,
                                    "请求相机权限",
                                    100,
                                    Manifest.permission.CAMERA
                                )
                            } else {
                                openCamera()
                            }

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
        openCamera()
    }

    private fun openCamera() {
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (captureIntent.resolveActivity(activity?.packageManager!!) != null) {
            var photoFile: File? = null
            var photoUri: Uri? = null
            if (isAndroidQ) {
                photoUri = createImageUri()
            } else {
                photoFile = createImageFile()
                photoFile?.let {
                    cameraPath = it.absolutePath
                    photoUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        FileProvider.getUriForFile(
                            requireContext(),
                            activity?.packageName.plus(".fileprovider"),
                            photoFile
                        )
                    } else {
                        Uri.fromFile(photoFile)
                    }
                }
            }
            mCamera = photoUri
            photoUri?.let {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                startActivityForResult(captureIntent, CAMERA_REQUEST_CODE)
            }
        }
    }

    private fun createImageFile(): File? {
        val imageName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date()).plus(".png")
        val storageDir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (!storageDir!!.exists()) {
            storageDir.mkdir()
        }
        val imageFile = File(storageDir, imageName)
        if (Environment.MEDIA_MOUNTED != EnvironmentCompat.getStorageState(imageFile)) {
            return null
        }
        return imageFile
    }

    private fun createImageUri(): Uri? {
        val status = Environment.getExternalStorageState()
        return if (status == Environment.MEDIA_MOUNTED) {
            context?.contentResolver?.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                ContentValues()
            )
        } else {
            context?.contentResolver?.insert(
                MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                ContentValues()
            )
        }
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

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICTURE_REQUEST_CODE -> {
                if (resultCode == RESULT_OK) {
                    vb.cir.load(data?.data) {
                        transformations(CircleCropTransformation())
                    }
                }
            }
            CAMERA_REQUEST_CODE -> {
                if (resultCode == RESULT_OK) {
                    if (isAndroidQ) {
                        vb.cir.load(mCamera) {
                            transformations(CircleCropTransformation())
                        }
                    } else {
                        vb.cir.load(File(cameraPath)) {
                            transformations(CircleCropTransformation())
                        }
                    }
                }

            }
        }

    }

}