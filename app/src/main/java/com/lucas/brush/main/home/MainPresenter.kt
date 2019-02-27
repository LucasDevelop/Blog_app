package com.lucas.brush.main.home

import com.lucas.brush.base.mvp.BaseUserPresenter
import com.lucas.brush.data.ApiServer
import com.lucas.brush.UserInfo
import javax.inject.Inject

class MainPresenter @Inject constructor(v: MainFragment, apiServer: ApiServer, userInfo: UserInfo) : BaseUserPresenter<MainFragment>(v, apiServer, userInfo) {
}

