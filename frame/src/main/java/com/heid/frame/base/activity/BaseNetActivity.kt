package com.heid.frame.base.activity

import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.heid.frame.R
import com.heid.frame.data.bean.IBean
import com.heid.frame.mvp.presenter.IPresenter
import com.heid.frame.mvp.view.IRequestView
import com.heid.frame.widget.loadingView.VaryViewHelperController
import javax.inject.Inject

/**
 * @package     com.heid.frame.base.activity
 * @author      lucas
 * @date        2018/11/24
 * @des         包含网络请求的页面
 */
abstract class BaseNetActivity<P : IPresenter<*>> : BaseDaggerActivity(), IRequestView {
    //注入控制器
    @Inject
    lateinit var mPresenter: P

    var mSwitchLayout: VaryViewHelperController? = null
    override fun initFindView() {
        super.initFindView()
        //如果页面需要有网络状态回显功能，那么在布局中必须使用frame_root_view id来包裹被切换的部分
        val frameRootView = findViewById<View>(R.id.frame_root_view)
        if (frameRootView != null)
            mSwitchLayout = VaryViewHelperController(frameRootView, getEmptyView())
    }

    //重写更换空数据布局
    open fun getEmptyView(): Int = -1

    //重新请求
    abstract fun reRequest()

    override fun requestFail(bean: IBean, code: Int, requestTag: Any) {
        //默认数据获取失败时显示服务器返回的错误信息
        ToastUtils.showShort(bean.msg)
    }

    override fun requestError(msg: String, exception: Throwable?, requestTag: Any) {
        //请求错误
        ToastUtils.showShort("服务器错误")
    }

    //token 过期
    override fun tokenOverdue() {
        ToastUtils.showShort("登录状态已失效，请重新登入.")
    }

    //显示加载中布局
    override fun showLoadingView() {
        mSwitchLayout?.showLoading()
    }

    //显示空布局
    override fun showEmptyView() {
        mSwitchLayout?.showEmpty(getString(R.string.frame_load_empty))
    }

    //显示网络错误布局
    override fun showNetErrorView() {
        mSwitchLayout?.showNetworkError { reRequest() }
    }

    //显示服务器错误布局
    override fun showServerErrorView(hint: String) {
        mSwitchLayout?.showNetworkError({ reRequest() }, hint)
    }
    override fun refreshView() {
        mSwitchLayout?.restore()
    }
}