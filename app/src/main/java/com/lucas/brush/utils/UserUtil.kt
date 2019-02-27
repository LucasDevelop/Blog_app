package com.lucas.brush.utils

import android.app.Activity
import com.lucas.brush.App

/**
 * @package     com.zhongde.tinglishi.utils
 * @author      lucas
 * @date        2018/12/7
 * @des
 */
object UserUtil {

    //用户是否登录
    fun isLogin(): Boolean {
        val tokenBean = App.instance?.userInfo?.userBean
        return tokenBean?.token != null && !tokenBean.token.isEmpty()
    }

    //强制登陆 --
    fun mustLogin(activity: Activity): Boolean {
        if (!isLogin()){
//            LoginActivity.launch(activity)
            return true
        }
        return false
    }
}