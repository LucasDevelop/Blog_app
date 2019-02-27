package com.lucas.brush

import com.heid.frame.FrameApp
import com.heid.frame.config.FrameInitConfig
import com.lucas.brush.dagger.DaggerAppComponent
import com.lucas.brush.inteceptor.ParamsInterceptor
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

/**
 * @package     com.heid.frame20181119.init
 * @author      lucas
 * @date        2018/11/19
 * @des         程序入口
 */
class App : FrameApp() {
    @Inject lateinit var userInfo: UserInfo


    companion object {
        var instance: App? =null
    }

    override fun initFrameConfig() {
        instance = this
        FrameInitConfig.BASE_URL = "http://39.106.118.17:8080"
        //初始化用户状态
        userInfo.initUserInfo()
        //加入插入器
        FrameInitConfig.addInterceptor(ParamsInterceptor())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
        return DaggerAppComponent.builder().create(this)
    }

}