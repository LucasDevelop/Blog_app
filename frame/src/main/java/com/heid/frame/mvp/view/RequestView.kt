package com.heid.frame.mvp.view

import com.heid.frame.data.api.BaseModel
import com.heid.frame.data.bean.IBean

/**
 * @package    com.heid.frame.mvp.view
 * @anthor     luan
 * @date       2019/2/22
 * @des
 */
abstract class RequestView:IRequestView {
    override fun requestSuccess(bean: IBean, requestMode: BaseModel.RequestMode, requestTag: Any) {
    }
}