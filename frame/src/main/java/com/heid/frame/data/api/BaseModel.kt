package com.heid.frame.data.api

import com.blankj.utilcode.util.NetworkUtils
import com.heid.frame.config.FrameConfig
import com.heid.frame.data.bean.IBean
import com.heid.frame.helper.CommentHelper
import com.heid.frame.mvp.view.IListView
import com.heid.frame.mvp.view.IRequestView
import com.heid.frame.mvp.view.ISwipeView
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.components.support.RxFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @file       BaseModelkt
 * @brief      网络请求解析
 * @author     lucas
 * @date       2018/4/12 0012
 * @version    V1.0
 * @par        Copyright (c):
 * @par History:
 *             version: zsr, 2017-09-23
 */
open class BaseModel(val iView: IRequestView) : CommentHelper {

    private val TAG = "net"
    //请求体
    lateinit var call: Observable<out IBean>
    //加载显示的样式
    var loadStyle = LoadStyle.NONE
    //请求模式
    var requestMode = RequestMode.NONE
    //成功回调
    var _success: (dataBean: IBean, reqMode: RequestMode,requestTag:String) -> Unit = { dataBean, _ ,_-> }
    //失败回调
    var _fail: (e: Throwable) -> Unit = {}
    //是否只用WiFi访问网络
    var isOnlyUseWifi = false
    //同步生命周期
    var isSyncLifeCycle = true
    //请求标记，用于在回调的时候区分数据
    var requestTag = ""

    enum class LoadStyle {
        NONE,//什么都不显示
        VIEW,//显示布局样式
        DIALOG,//显示popupWindow样式
        CUSTOM//显示自定义样式
    }

    enum class RequestMode {
        NONE,//首次加载
        LOAD_MODE//加载更多
    }

    //自定义样式
    var _customProgress: () -> Unit = {}
    var _customError: () -> Unit = {}

    fun request(init: BaseModel.() -> Unit) {
        //初始化
        init()
        loadBeginView()
        val checkNetWork = checkNetWork()
        if (checkNetWork) {
            loadNetErrorView()
            return
        }
        //请求数据
        val observable = call.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
        //同步生命周期
        if (isSyncLifeCycle) {
            if (iView is RxAppCompatActivity)
                observable?.compose(iView.bindUntilEvent(ActivityEvent.DESTROY))
            if (iView is RxFragment)
                observable?.compose(iView.bindUntilEvent(FragmentEvent.DESTROY))
        }
        val subscribe = observable?.subscribe({
            if (it is IBean && it.status != null) {
                if (it.status==200){
                    iView.requestSuccess(it, requestMode, requestTag)
                    onRequestSuccess(it)
                }else{
                    iView.requestFail(it,it.status,requestTag)
                    disProgressError()
                }
            } else {
                //code为空，或者json解析错误
                throw java.lang.RuntimeException("code为空，或者json解析错误")
            }
        }, {
            //输出日志
            it.printStackTrace()
            iView.requestError(it.message ?: "未知错误", it, requestTag)
            //失败
            loadFailView()
            _fail(it)
        }, {
            //结束
        })
    }

    //显示网络错误
    private fun loadNetErrorView() {
        when (loadStyle) {
            LoadStyle.NONE -> {

            }
            LoadStyle.DIALOG -> {
                iView.hideLoading()
            }
            LoadStyle.VIEW -> {
                iView.showNetErrorView()
            }
            LoadStyle.CUSTOM -> {
            }
        }
    }

    private fun onRequestSuccess(data: Any) {
        if (iView is ISwipeView)
            iView.resetRefresh()
        if (data is IBean)
            when (data.status) {
                FrameConfig.Net.REQUEST_SUCCESS -> {
                    _success(data, requestMode,requestTag)
                    disProgressSuccess(data)
                }
                FrameConfig.Net.TOKEN_OVERDUE -> {
                    iView.tokenOverdue()
                }
                else -> {
                    //其他状态码
                    iView.requestFail(data, data.status!!,requestTag )
                    disProgressError()
                }
            }
    }

    //请求数据失败时关闭progress
    private fun disProgressError() {
        when (requestMode) {
            RequestMode.NONE -> {
                when (loadStyle) {
                    LoadStyle.NONE -> {
                    }
                    LoadStyle.VIEW -> {
                        iView.showServerErrorView("服务器错误")
                    }
                    LoadStyle.DIALOG -> {
                        iView.hideLoading()
                    }
                    LoadStyle.CUSTOM -> {
                        _customError()
                    }
                }
            }
            RequestMode.LOAD_MODE -> {
                (iView as? IListView)?.showLoadMoreFailView()
            }
        }
    }

    //请求数据成功时关闭progress
    private fun disProgressSuccess(data: IBean) {
        if (requestMode == RequestMode.NONE)
            when (loadStyle) {
                LoadStyle.NONE -> {
                }
                LoadStyle.VIEW -> {
                    if (data.isEmpty())
                        iView.showEmptyView()
                    else
                        iView.refreshView()
                }
                LoadStyle.DIALOG -> {
                    iView.hideLoading()
                }
                LoadStyle.CUSTOM -> {
                    _customError()
                }
            }
        else {//load more
            (iView as? IListView)?.showLoadMoreSuccessView()
            when (loadStyle) {
                LoadStyle.DIALOG -> {
                    iView.hideLoading()
                }
                LoadStyle.CUSTOM -> {
                    _customError()
                }
                else -> {
                }
            }
        }
    }

    //预加载view样式
    private fun loadBeginView() {
        when (requestMode) {
            RequestMode.NONE -> {
                when (loadStyle) {
                    LoadStyle.NONE -> {
                    }
                    LoadStyle.VIEW -> {
                        iView.showLoadingView()
                    }
                    LoadStyle.DIALOG -> {
                        iView.showLoading()
                    }
                    LoadStyle.CUSTOM -> {
                        _customProgress()
                    }
                }
            }
            RequestMode.LOAD_MODE -> {
            }
        }
    }

    //加载失败的样式
    private fun loadFailView() {
        when (requestMode) {
            RequestMode.NONE -> {
                when (loadStyle) {
                    LoadStyle.NONE -> {
                    }
                    LoadStyle.VIEW -> {
                        iView.showServerErrorView("服务器错误")
                    }
                    LoadStyle.DIALOG -> {
                        iView.hideLoading()
                    }
                    LoadStyle.CUSTOM -> {
                        _customError()
                    }
                }
            }
            RequestMode.LOAD_MODE -> {
                (iView as? IListView)?.showLoadMoreFailView()
            }
        }
        (iView as? ISwipeView)?.resetRefresh()
    }

    private fun checkNetWork(): Boolean {
        //判断wifi是否可用
        if (isOnlyUseWifi && !NetworkUtils.isWifiConnected()) {
            "wifi不可用".ld(TAG)
            "wifi不可用".showToast()
            _fail(RuntimeException("wifi不可用"))
            return true
        }
        //判断网络是否可用
        if (!isOnlyUseWifi && !NetworkUtils.isConnected()) {
            "网络不可用".ld(TAG)
            "网络不可用".showToast()
            _fail(RuntimeException("网络不可用"))
            return true
        }
        return false
    }
}