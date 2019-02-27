package com.heid.frame.config

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

/**
 * @package     com.heid.frame.config
 * @author      lucas
 * @date        2018/11/19
 * @des         框架配置---所有方法调用建议在application中
 */
object FrameInitConfig {
    //拦截器
    val interceptors = ArrayList<Interceptor>()
    //域名
    var BASE_URL = ""

    //拍照图片路径
    var CAMERA_PIC_URI = ""

    init {
        //添加默认的拦截器
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        interceptors.add(loggingInterceptor)
    }

    //添加okhttp拦截器
    fun addInterceptor(interceptor: Interceptor): FrameInitConfig {
        interceptors.add(interceptor)
        return this
    }
}