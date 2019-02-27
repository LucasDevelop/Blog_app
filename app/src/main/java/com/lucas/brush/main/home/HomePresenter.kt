package com.lucas.brush.main.home

import com.lucas.brush.UserInfo
import com.lucas.brush.base.mvp.BaseUserPresenter
import com.lucas.brush.data.ApiServer
import javax.inject.Inject

/**
 * @package    com.lucas.brush.main.home
 * @anthor     luan
 * @date       2019/2/16
 * @des
 */
class HomePresenter @Inject constructor(v: HomeActivity, apiServer: ApiServer, userInfo: UserInfo) : BaseUserPresenter<HomeActivity>(v, apiServer, userInfo)  {

    fun test(){

    }
}