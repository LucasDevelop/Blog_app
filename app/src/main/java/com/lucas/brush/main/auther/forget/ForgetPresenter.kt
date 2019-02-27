package com.lucas.brush.main.auther.forget

import com.lucas.brush.base.mvp.BaseUserPresenter
import com.lucas.brush.data.ApiServer
import com.lucas.brush.UserInfo
import javax.inject.Inject

/**
 * @package    ForgetPresenter.kt
 * @anthor     luan
 * @date       10:16 AM
 * @des
 */
class ForgetPresenter @Inject constructor(v: ForgetActivity, apiServer: ApiServer, userInfo: UserInfo) : BaseUserPresenter<ForgetActivity>(v, apiServer, userInfo) {
    fun forget(name: String, password: String) {
        request {
            call = apiServer.forget(name, password)
            _success = { dataBean, reqMode, requestTag ->
                dataBean.msg.showToast()
                //修改成功
                finish()
            }
        }
    }
}

