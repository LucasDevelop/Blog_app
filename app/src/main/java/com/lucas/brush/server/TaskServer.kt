package com.lucas.brush.server

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.heid.frame.bus.AppBus
import com.heid.frame.data.api.BaseModel
import com.heid.frame.data.bean.IBean
import com.heid.frame.mvp.view.IRequestView
import com.lucas.brush.UserInfo
import com.lucas.brush.data.ApiServer
import com.lucas.brush.main.personal.blogs.BlogsBean
import com.lucas.brush.receiver.NetworkConnChangeReceiver
import com.squareup.otto.Subscribe
import dagger.android.AndroidInjection
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

/**
 * @package    com.lucas.brush.server
 * @anthor     luan
 * @date       2019/2/22
 * @des        自动人物刷新
 */
class TaskServer : Service(), IRequestView {
    override fun onBind(intent: Intent): IBinder = TaskBinder()

    @Inject
    lateinit var userInfo: UserInfo
    @Inject
    lateinit var apiServer: ApiServer
    @Inject
    lateinit var appBus: AppBus


    open inner class TaskBinder : Binder() {
        fun startTask() {
            autoBrush()
        }

        fun stopTask() {

        }
    }

    //自动任务
    inner class AutoTask : Thread {
        var isRunning = false
        lateinit var data: List<BlogsBean.DataBean>
        var index = 0//当前任务位置
        var failCount = 0//失败任务数量

        constructor(data: List<BlogsBean.DataBean>) : super() {
            if (isRunning) return
            isRunning = true
            this.data = data
            index = 0
            failCount = 0
        }

        override fun run() {
            data.forEach {
                if (!isRunning) return
                try {
                    brush(it)
                } catch (e: Exception) {
                    e.printStackTrace()
                    failCount++
                }
            }
            taskComplete()
        }

        //任务结束上报结果
        private fun taskComplete() {
            ToastUtils.showShort("完成任务！")
        }

        fun brush(bean: BlogsBean.DataBean) {
            val conn = URL(bean.blogUrl).openConnection() as HttpURLConnection
            conn.connect()
            conn.inputStream
            conn.disconnect()
            Log.d("lucas", "完成任务：${bean.blogName}")
        }

        fun stopBrush() {
            if (!isRunning) return
            isRunning = false
        }
    }

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
        appBus.register(this)
    }

    //开始人物自动刷流量
    fun autoBrush() {
        //判断网络状态
        if (!NetworkUtils.isConnected()) {
            Log.d("lucas", "当前无网络.")
            return
        }
        //上传任务IP
        BaseModel(this).request {
            call = apiServer.addIP(NetworkUtils.getIPAddress(true))
            requestTag = "add_ip"
        }

    }

    //监听网络状态
    @Subscribe
    fun onReceiverNetStateChange(netInfoEvent: NetworkConnChangeReceiver.NetInfoEvent) {
        //网络状态改变，检查是否执行任务

    }

    override fun requestSuccess(bean: IBean, requestMode: BaseModel.RequestMode, requestTag: Any) {
        if (bean is BlogsBean) {
            if (bean.data != null) {
                AutoTask(bean.data).start()
            }
        }
        if (requestTag == "add_ip") {
            if (bean.status == 200) {
                Log.d("lucas", "开始获取任务列表")
                //获取任务列表
                BaseModel(this).request {
                    call = apiServer.getAllBlog()
                }
            } else {
                Log.d("lucas", "此IP今日已执行过任务。")
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        appBus.unregister(this)
    }

    override fun requestFail(bean: IBean, code: Int, requestTag: Any) {
    }

    override fun requestError(msg: String, exception: Throwable?, requestTag: Any) {
    }

    override fun tokenOverdue() {
    }

    override fun refreshView() {
    }

    override fun showLoadingView() {
    }

    override fun showEmptyView() {
    }

    override fun showNetErrorView() {
    }

    override fun showServerErrorView(hint: String) {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

}