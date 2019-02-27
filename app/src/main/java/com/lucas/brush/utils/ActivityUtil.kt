package com.lucas.brush.utils

import android.app.ActivityManager
import android.app.Service
import android.content.Context

/**
 * @package    com.lucas.brush.utils
 * @anthor     luan
 * @date       2019/2/25
 * @des
 */
object ActivityUtil {

    //判断service是否正在启动--只能用于判断自己的服务
    fun <S : Service> isRunningService(context: Context, clazz: Class<S>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
        if (manager != null) {
            val services = manager.getRunningServices(30)
            services?.forEach {
                if (it.service.className == clazz.toString()) return true
            }
        }
        return false
    }
}