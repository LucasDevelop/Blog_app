package com.heid.frame.base.fragment

import android.app.Activity
import android.os.Handler
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * @package     com.heid.frame.base.fragment
 * @author      lucas
 * @date        2018/11/26
 * @des         提供注入功能且无网络请求的界面
 */
abstract class BaseDaggerFragment : BaseFragment() {
    @Inject
    lateinit var mHandler: Handler

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        //如果这里运行报错，请检查fragment对应的presenter是否在构造添加的@inject注入
        AndroidSupportInjection.inject(this)
    }
}