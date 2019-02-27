package com.heid.frame

import android.content.Context
import android.os.Handler
import android.support.multidex.MultiDex
import com.blankj.utilcode.util.Utils
import com.heid.frame.config.FrameInitConfig
import dagger.android.DaggerApplication
import java.io.File
import javax.inject.Inject

/**
 * @package     com.heid.frame
 * @author      lucas
 * @date        2018/11/16
 * @des         程序入口
 */
abstract class FrameApp : DaggerApplication() {

    @Inject
    lateinit var mHandler: Handler



    companion object {
        lateinit var mBaseApp: FrameApp
    }

    override fun onCreate() {
        mBaseApp = this
        super.onCreate()
        appInit()
        initFrameConfig()
    }

    private fun appInit() {
        Utils.init(this)
        FrameInitConfig.CAMERA_PIC_URI = File(cacheDir,"/file/camera").absolutePath
    }

    //框架初始化
    abstract fun initFrameConfig()

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}