package com.lucas.brush.base.mvp

import com.heid.frame.mvp.presenter.IPresenter
import com.heid.frame.mvp.view.IRequestView
import com.lucas.brush.data.ApiServer

/**
 * @package     com.zhongde.tinglishi.base.mvp
 * @author      lucas
 * @date        2018/12/5
 * @des
 */
abstract class BasePresenter<out V : IRequestView>(v: V, var apiServer: ApiServer) : IPresenter<V>(v)