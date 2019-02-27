package com.lucas.brush.main.auther.register

import com.lucas.brush.base.mvp.BaseUserPresenter
import com.lucas.brush.data.ApiServer
import com.lucas.brush.UserInfo
import javax.inject.Inject

/**
 * @package    RegisterPresenter.kt
 * @anthor     luan
 * @date       10:03 AM
 * @des
 */
class RegisterPresenter @Inject constructor(v: RegisterActivity, apiServer: ApiServer, userInfo: UserInfo) : BaseUserPresenter<RegisterActivity>(v, apiServer, userInfo) {


    fun register(name: String, password: String) {
        request {
            call = apiServer.register(name, password)
            _success = { dataBean, reqMode, requestTag ->
                dataBean.msg.showToast()
                //注册成功
                finish()
            }
        }
    }
}

