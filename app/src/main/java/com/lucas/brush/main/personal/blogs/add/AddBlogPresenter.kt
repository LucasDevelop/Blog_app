package com.lucas.brush.main.personal.blogs.add

import com.lucas.brush.base.mvp.BaseUserPresenter
import com.lucas.brush.data.ApiServer
import com.lucas.brush.UserInfo
import javax.inject.Inject

/**
 * @package    AddBlogPresenter.kt
 * @anthor     luan
 * @date       9:33 AM
 * @des
 */
class AddBlogPresenter @Inject constructor(v: AddBlogActivity, apiServer: ApiServer, userInfo: UserInfo) : BaseUserPresenter<AddBlogActivity>(v, apiServer, userInfo) {

    fun add(name: String, url: String) {
        request {
            call = apiServer.addBlog(userInfo.userBean.id, name, url)
            _success = { dataBean, reqMode, requestTag ->
                dataBean.msg.showToast()
                if (dataBean.status == 200)
                    finish()
            }
        }
    }
}

