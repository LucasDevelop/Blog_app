package com.lucas.brush.main.task

import com.lucas.brush.base.mvp.BaseUserPresenter
import com.lucas.brush.data.ApiServer
import com.lucas.brush.UserInfo
import javax.inject.Inject

class TaskPresenter @Inject constructor(v: TaskFragment, apiServer: ApiServer, userInfo: UserInfo) : BaseUserPresenter<TaskFragment>(v, apiServer, userInfo) {
}

