package com.heid.frame.mvp.presenter

import android.app.Activity
import com.heid.frame.data.api.BaseModel
import com.heid.frame.mvp.view.IRequestView

/**
 * @package     com.heid.frame.mvp.presenter
 * @author      lucas
 * @date        2018/11/16
 * @des         控制器
 */
abstract class IPresenter<out V : IRequestView>(val v: V) {

    fun request(init: BaseModel.() -> Unit) {
        //每个请求都使用新的BaseModel保证请求不冲突
        BaseModel(v).request(init)

    }

    //关闭页面
    fun finish() {
        (v as? Activity)?.finish()
    }
}