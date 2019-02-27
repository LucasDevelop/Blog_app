package com.lucas.brush.main

import com.lucas.brush.base.mvp.BaseUserPresenter
import com.lucas.brush.data.ApiServer
import com.lucas.brush.UserInfo
import javax.inject.Inject

class SplashPresenter @Inject constructor(v: SplashActivity, apiServer: ApiServer, userInfo: UserInfo) : BaseUserPresenter<SplashActivity>(v, apiServer, userInfo) {

    fun test(){

    }
}

