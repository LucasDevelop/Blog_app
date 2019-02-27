package com.lucas.brush.main.personal

import com.lucas.brush.base.mvp.BaseUserPresenter
import com.lucas.brush.data.ApiServer
import com.lucas.brush.UserInfo
import javax.inject.Inject

class PersonalPresenter @Inject constructor(v: PersonalFragment, apiServer: ApiServer, userInfo: UserInfo) : BaseUserPresenter<PersonalFragment>(v, apiServer, userInfo) {
}

