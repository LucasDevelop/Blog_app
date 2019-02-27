package com.heid.frame.base.fragment

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heid.frame.bus.AppBus
import com.heid.frame.dialog.LoadingDialog
import com.heid.frame.helper.CommentHelper
import com.heid.frame.mvp.view.IView
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * @package     com.heid.frame.base
 * @author      lucas
 * @date        2018/11/26
 * @des         Fragment底层基类--包含一些基本的功能,一般用于无网络请求无dagger注入的界面
 */
abstract class BaseFragment : RxFragment(), IView,CommentHelper {
    val mLoadingDialog: LoadingDialog by lazy { LoadingDialog(activity!!) }
    val mActivity:FragmentActivity by lazy { activity!! }
    lateinit var rootView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = LayoutInflater.from(activity).inflate(getLayoutID(), null)
        return rootView
    }

    //视图创建完毕后
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        baseInit()
        initView(savedInstanceState)
        initData()
        initEvent()
    }

    //内部初始化
    private fun baseInit() {
        if (registerBus())
            AppBus.register(this)
        initFindView()
    }

    //查找控件
    open fun initFindView() {
    }

    //布局id
    abstract fun getLayoutID(): Int

    //视图初始化
    abstract fun initView(savedInstanceState: Bundle?)

    //数据初始化
    abstract fun initData()

    //事件初始化
    open fun initEvent() {}

    //注册事件
    open fun registerBus() = false

    //显示加载框
    override fun showLoading() {
        if (!mLoadingDialog.isShowing)
            mLoadingDialog.show()
    }

    //隐藏加载框
    override fun hideLoading() {
        if (mLoadingDialog.isShowing)
            mLoadingDialog.dismiss()
    }

    fun <T : View> findView(id: Int): T? {
        return rootView.findViewById(id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (registerBus())
            AppBus.unregister(this)
    }
}