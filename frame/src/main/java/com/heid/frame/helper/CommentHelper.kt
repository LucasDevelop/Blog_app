package com.heid.frame.helper

import android.app.Activity
import android.arch.lifecycle.LifecycleObserver
import android.content.Context
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.heid.frame.FrameApp
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * @创建者     lucas
 * @创建时间   2017/12/25 0025 16:33
 * @描述         通用型工具
 */
interface CommentHelper : LifecycleObserver {


    /**
     * 扩展函数
     * 对Any对象扩展toString方法，消除空指针异常
     */
    fun Any?.ts(): String = if (this == null) "null" else toString()

    /**
     * 对Any对象扩展log方法
     */
    fun Any?.ld(tag: String = "lucas") {
        LogUtils.d(tag, this.toString())
    }

    fun Any?.le(tag: String = "lucas") {
        LogUtils.e(tag, this.toString())
    }

    fun String?.showToast() {
        //判断当前线程是否是主线程
        if (Thread.currentThread() == Looper.getMainLooper().thread)
            Toast.makeText(FrameApp.mBaseApp, this?:"null", Toast.LENGTH_SHORT).show()
        else
            FrameApp.mBaseApp.mHandler.post { Toast.makeText(FrameApp.mBaseApp, this?:"null", Toast.LENGTH_SHORT).show() }
    }

    /**
     * 资源获取
     */
    fun Activity.getResStr(strIdRes: Int) = this.resources.getString(strIdRes) ?: "null"

    fun Activity.getResColor(colorIdRes: Int) = this.resources.getColor(colorIdRes)

    //三元运算
    infix fun <A> A?.T(isFalse: A): A {
        if (this == null || (this is String && this == "")) {
            return isFalse
        } else
            return this
    }

    //设置点击事件
    infix fun Array<out View>.setOnClickListener(listener: View.OnClickListener) {
        this.forEach { it.setOnClickListener(listener) }
    }

    //表单验证
    fun Map<Boolean, String>.formCheck(callback: () -> Unit = {}): Boolean {
        this.forEach {
            if (it.key) {
                ToastUtils.showShort(it.value)
                return false//未通过
            }
        }
        //调用回掉
        callback()
        return true//验证通过
    }

    //绑定生命周期
    fun bindLifeCycle(context: Context){
        (context as? AppCompatActivity)?.lifecycle?.addObserver(this)
    }

    //解除绑定
    fun unbindLifeCycle(context: Context){
        (context as? AppCompatActivity)?.lifecycle?.removeObserver(this)
    }
}