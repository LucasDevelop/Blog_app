package com.lucas.brush.receiver

import android.content.*
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.IBinder
import android.os.Parcelable
import android.util.Log
import com.heid.frame.bus.AppBus
import com.lucas.brush.server.TaskServer
import com.lucas.brush.utils.ActivityUtil
import javax.inject.Inject

/**
 * @package    com.lucas.brush.receiver
 * @anthor     luan
 * @date       2019/2/22
 * @des        Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE,android.permission.ACCESS_WIFI_STATE
 */
class NetworkConnChangeReceiver : BroadcastReceiver() {
    @Inject
    lateinit var appBus: AppBus
    val infoEvent = NetInfoEvent()
    val TAG = "NetworkConnChange"

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == WifiManager.NETWORK_STATE_CHANGED_ACTION) {
            //检查网络是否有效--链接上不代表有网络
            val netInfo = intent.getParcelableExtra<Parcelable>(WifiManager.EXTRA_NETWORK_INFO)
            if (netInfo != null && netInfo is NetworkInfo) {
                when {
                    netInfo.type == ConnectivityManager.TYPE_WIFI -> {
                        Log.d(TAG, "当前网络Wi-Fi链接")
                        infoEvent.netType = ConnectivityManager.TYPE_WIFI
                    }
                    netInfo.type == ConnectivityManager.TYPE_MOBILE -> {
                        Log.d(TAG, "当前网络移动网络链接")
                        infoEvent.netType = ConnectivityManager.TYPE_MOBILE
                    }
                    else -> Log.d("lucas", "当前无网络链接")
                }
                val isConn = netInfo.state == NetworkInfo.State.CONNECTED
                Log.d(TAG, "isConn:$isConn")
                //获取网络类型
                infoEvent.isConnected = isConn
                if (isConn){
                    //判断服务是否开启
                    if (ActivityUtil.isRunningService(context,TaskServer::class.java)){
                        appBus.post(infoEvent)
                    }else{
                        Log.d(TAG,"服务未启动，尝试启动服务")
                        //开启服务
                        openServer(context)
                    }
                }
            }
        }
        if (WifiManager.WIFI_STATE_CHANGED_ACTION == action) {//Wi-Fi   open/close
            val wifiStatus = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0)
            Log.d(TAG, "wifi status:$wifiStatus")
            when (wifiStatus) {
                WifiManager.WIFI_STATE_DISABLED -> {//Wi-Fi未开启
                }
                WifiManager.WIFI_STATE_DISABLING -> {//Wi-Fi关闭中
                }
                WifiManager.WIFI_STATE_ENABLING -> {//Wi-Fi开启中

                }
                WifiManager.WIFI_STATE_ENABLED -> {//Wi-Fi已开启

                }
                WifiManager.WIFI_STATE_UNKNOWN -> {//Wi-Fi状态未知

                }
            }
        }
    }

    val conn = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            var taskBinder = service as? TaskServer.TaskBinder
            if (taskBinder != null) {
                taskBinder.startTask()
            }

        }
    }

    //tips:服务未关闭
    fun openServer(context: Context) {
        context.bindService(Intent(context, TaskServer::class.java), conn, Context.BIND_AUTO_CREATE)
    }

    open class NetInfoEvent {
        var isConnected = false
        var netType = ConnectivityManager.TYPE_WIFI
    }
}