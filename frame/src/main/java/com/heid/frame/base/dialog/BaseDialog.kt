package com.heid.frame.base.dialog

import android.app.Activity
import android.app.Dialog
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.heid.frame.helper.CommentHelper

/**
 * @package     com.heid.frame.base
 * @author      lucas
 * @date        2018/11/24
 * @des         dialog基类
 */
abstract class BaseDialog(context: Context) : Dialog(context),CommentHelper {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindLifeCycle(context)
        baseInit()
        initView()
    }

    private fun baseInit() {
        if (getLayoutID() != null) {
            setContentView(getLayoutID()!!)
        }
        //居中
        val attributes = window.attributes
        attributes.gravity = Gravity.CENTER
        //宽度包裹
        attributes.width = WindowManager.LayoutParams.WRAP_CONTENT
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT
    }

    abstract fun initView()

    abstract fun getLayoutID(): Int?

    override fun show() {
        //界面关闭不再显示dialog避免空指针
        if (context is Activity && (context as Activity).isDestroyed)
            return
        super.show()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        if (isShowing) dismiss()
        unbindLifeCycle(context)
    }
}