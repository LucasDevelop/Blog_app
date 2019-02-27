package com.heid.frame.dialog

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.heid.frame.R
import com.heid.frame.base.activity.BaseActivity
import com.heid.frame.base.dialog.BaseDialog
import com.heid.frame.config.FrameInitConfig
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.frame_dialog_select_pic.*
import java.io.File
import javax.inject.Inject

/**
 * @package    com.heid.frame.dialog
 * @anthor     lucas
 * @date       2018/12/11
 * @des        头像选择弹窗
 */
class SelectPicDialog<A:BaseActivity> @Inject constructor(val context: A) : BaseDialog(context), View.OnClickListener {
    val OPEN_CAMERA_RESULT = 0x001
    val OPEN_CROP_RESULT = 0x002
    val OPEN_ALBUM_RESULT = 0x003

    var isCrop: Boolean = true

    lateinit var picFile: File
    lateinit var picUri: Uri
    lateinit var picCrop: File//裁剪后的图片
    lateinit var picCropUri: Uri
    lateinit var bitmapCropPic: Bitmap


    var onPicResult: (Uri) -> Unit = {}//拍照结束
    var onCropResult: (Uri, Bitmap) -> Unit = { _, _ -> }//裁剪结束

    override fun initView() {
        val attributes = window.attributes
        attributes.gravity = Gravity.BOTTOM
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT

        arrayOf(v_photo, v_camera, v_cancel).setOnClickListener(this)
        context.onACResult = { requestCode, resultCode, data ->
            if (resultCode == Activity.RESULT_OK) {
                when (requestCode) {
                    OPEN_CAMERA_RESULT -> {//相机返回
                        "照片路径${picFile.absoluteFile}".ld()
                        onPicResult(picUri)
                        //裁剪
                        if (isCrop) {
                            cropPic()
                        }
                    }
                    OPEN_CROP_RESULT -> {//裁剪返回
                        try {
                            bitmapCropPic = BitmapFactory.decodeStream(context.contentResolver.openInputStream(picCropUri))
                            onCropResult(picCropUri, bitmapCropPic)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            "bitmap 转换失败".le()
                        }
                    }
                    OPEN_ALBUM_RESULT -> {//相册返回
                        "照片路径${picFile.absoluteFile}".ld()
                        onPicResult(picUri)
                        //裁剪
                        if (isCrop) {
                            cropPic()
                        }
                    }
                }
            }
        }
    }

    //裁剪图片
    private fun cropPic() {
        try {
            picCrop = File(FrameInitConfig.CAMERA_PIC_URI, "${System.currentTimeMillis()}.jpg")
            if (picCrop.exists()) picCrop.delete()
            picCrop.createNewFile()
        } catch (e: Exception) {
            e.printStackTrace()
            "裁剪图片创建文件失败".le()
        }
        //调用系统裁剪功能
        val intent = Intent("com.android.camera.action.CROP")
        picCropUri = Uri.fromFile(picCrop)
        intent.setDataAndType(picCropUri, "image/*")
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        intent.putExtra("crop", "true")
        intent.putExtra("scale", true)

        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)

        //输出的宽高

        intent.putExtra("outputX", 300)
        intent.putExtra("outputY", 300)

        intent.putExtra("return-data", false)

        //输出路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picCropUri)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("noFaceDetection", true) // no face detection
        context.startActivityForResult(intent, OPEN_CROP_RESULT)
    }

    override fun getLayoutID(): Int? = R.layout.frame_dialog_select_pic

    override fun onClick(v: View) {
        when (v) {
            v_photo -> {//从相册选择
                openAlbum()
            }
            v_camera -> {//照相
                openSystemCamera()
            }
            v_cancel -> {//取消
                dismiss()
            }
        }

    }

    //打开系统相册
    private fun openAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        createPicFIle()
        intent.setDataAndType(picUri, "image/*")
        context.startActivityForResult(intent, OPEN_ALBUM_RESULT)
    }


    //打开系统相机
    private fun openSystemCamera() {
        //获取相机权限
        RxPermissions(context)
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe {
                    if (it) {
                        val intent = Intent("android.media.action.IMAGE_CAPTURE")
                        if (intent.resolveActivity(context.packageManager) != null) {
                            try {
                                //文件夹不存则创建
                                createPicFIle()
                            } catch (e: Exception) {
                                e.printStackTrace()
                                "文件创建失败".le()
                            }
                            //设置拍照后图片路径
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri)
                            context.startActivityForResult(intent, OPEN_CAMERA_RESULT)
                        }
                    } else {
                        "拒绝权限会导致功能无法正常使用".showToast()
                    }
                }
    }

    fun createPicFIle() {
        val file = File(FrameInitConfig.CAMERA_PIC_URI)
        if (file.exists()) file.delete()
        file.mkdirs()
        picFile = File(file, "${System.currentTimeMillis()}.jpg")
        picFile.createNewFile()
        //7.0以上的系统要分开处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            picUri = FileProvider.getUriForFile(context, "${context.packageName}.provider", picFile)
        } else {
            picUri = Uri.fromFile(picFile)
        }
    }
}