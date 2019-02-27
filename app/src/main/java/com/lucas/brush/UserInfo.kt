package com.lucas.brush

import android.text.TextUtils
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.lucas.brush.data.bean.TokenBean
import com.lucas.brush.data.bean.UserBean

/**
 * @package     com.heid.frame20181119
 * @author      lucas
 * @date        2018/11/22
 * @des         用户信息
 */

class UserInfo constructor(var gson: Gson) {

    val KEY_USER = "userInfo"

    var userBean = UserBean()


    //刷新token
    fun refreshUserBean(userBean: UserBean) {
        this.userBean = userBean
        refreshSP()
    }

    //退出登陆
    fun logout() {
        userBean = UserBean()
        SPUtils.getInstance().remove(KEY_USER)
    }

    //判断登陆状态
    fun isLogin() = !userBean.token.isNullOrEmpty()

    //将缓存数据添加到内存中
    fun initUserInfo() {
        val userInfoStr = SPUtils.getInstance().getString(KEY_USER)
        if (!TextUtils.isEmpty(userInfoStr)) {
            userBean = gson.fromJson(userInfoStr, UserBean::class.java)
        }
    }

    //刷新sp
    private fun refreshSP() {
        SPUtils.getInstance().put(KEY_USER, gson.toJson(userBean))
    }
}