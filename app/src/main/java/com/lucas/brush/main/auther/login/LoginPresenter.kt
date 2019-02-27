package com.lucas.brush.main.auther.login

import com.lucas.brush.base.mvp.BaseUserPresenter
import com.lucas.brush.data.ApiServer
import com.lucas.brush.UserInfo
import com.lucas.brush.data.bean.LoginBean
import com.lucas.brush.main.home.HomeActivity
import javax.inject.Inject

/**
 * @package    LoginPresenter.kt
 * @anthor     luan
 * @date       5:01 PM
 * @des
 */
class LoginPresenter @Inject constructor(v: LoginActivity, apiServer: ApiServer, userInfo: UserInfo) : BaseUserPresenter<LoginActivity>(v, apiServer, userInfo) {

    fun login(name: String, pwd: String) {
        request {
            call = apiServer.login(name, pwd)
            _success = { dataBean, reqMode, requestTag ->
                dataBean.msg.showToast()
                //刷新缓存
                if (dataBean is LoginBean)
                    userInfo.refreshUserBean(dataBean.data)
                //进入主页
                HomeActivity.launch(v)
            }
        }
    }
}

