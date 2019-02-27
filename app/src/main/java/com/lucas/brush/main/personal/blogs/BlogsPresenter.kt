package com.lucas.brush.main.personal.blogs

import com.lucas.brush.base.mvp.BaseUserPresenter
import com.lucas.brush.data.ApiServer
import com.lucas.brush.UserInfo
import javax.inject.Inject

/**
 * @package    BlogsPresenter.kt
 * @anthor     luan
 * @date       10:09 AM
 * @des
 *
 */
class BlogsPresenter @Inject constructor(v: BlogsActivity, apiServer: ApiServer, userInfo: UserInfo) : BaseUserPresenter<BlogsActivity>(v, apiServer, userInfo) {

    fun getBlogList() {
        request {
            call = apiServer.getBlogList(userInfo.userBean.id)
        }
    }

    fun delBlog(id: Int) {
        request {
            call = apiServer.delBlog(id)
            _success = { dataBean, reqMode, requestTag ->
                dataBean.msg.showToast()
                if (dataBean.status == 200)
                    getBlogList()
            }
        }
    }
}

