package com.lucas.brush.base.mvp

import com.heid.frame.mvp.view.IRequestView
import com.lucas.brush.data.ApiServer
import com.lucas.brush.UserInfo

/**
 * @package    com.zhongde.tinglishi.base.mvp
 * @anthor     lucas
 * @date       2018/12/21
 * @des        用户登陆后的界面使用
 */
abstract class BaseUserPresenter<out V : IRequestView>(v: V, apiServer: ApiServer, var userInfo: UserInfo) : BasePresenter<V>(v, apiServer) {

    //刷新用户信息
    fun refreshUserInfo(onRefreshSuccess: () -> Unit = {}) {
//        request {
//            loadStyle = BaseModel.LoadStyle.DIALOG
//            call = apiServer.refreshUserInfo()
//            _success = { dataBean, _, _ ->
//                //更新内存和本地缓存
//                if (dataBean is RefreshUserBean){
//                    userInfo.refreshUserBean(dataBean.data)
//                    onRefreshSuccess()
//                }
//            }
//        }
    }
}