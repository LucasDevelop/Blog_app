package com.lucas.brush.main.task

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import com.lucas.brush.R
import com.heid.frame.base.fragment.BaseNetFragment
import com.heid.frame.data.api.BaseModel
import com.heid.frame.data.bean.IBean
import com.lucas.brush.server.TaskServer
import kotlinx.android.synthetic.main.fragment_task.*

/**
 * @package    TaskFragment.kt
 * @anthor     luan
 * @date       4:26 PM
 * @des        任务中心
 */
class TaskFragment : BaseNetFragment<TaskPresenter>(), View.OnClickListener {

    override fun getLayoutID(): Int = R.layout.fragment_task

    val connServer = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as? TaskServer.TaskBinder
            binder?.apply { startTask() }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        arrayOf(v_start).setOnClickListener(this)
    }

    override fun initData() {
    }

    override fun onClick(v: View) {
        when (v) {
            v_start -> {
                //绑定服务并开始任务
                mActivity.bindService(Intent(mActivity, TaskServer::class.java), connServer, Context.BIND_AUTO_CREATE)
            }
            else -> {
            }
        }
    }

    override fun requestSuccess(bean: IBean, requestMode: BaseModel.RequestMode, requestTag: Any) {
    }

    override fun reRequest() {
    }


    override fun onDestroy() {
        super.onDestroy()
        mActivity.unbindService(connServer)
    }
}

