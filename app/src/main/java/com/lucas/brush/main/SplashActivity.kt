package com.lucas.brush.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.lucas.brush.R
import com.heid.frame.base.activity.BaseNetActivity
import com.heid.frame.data.api.BaseModel
import com.heid.frame.data.bean.IBean
import com.lucas.brush.App
import com.lucas.brush.UserInfo
import com.lucas.brush.main.auther.login.LoginActivity
import com.lucas.brush.main.home.HomeActivity
import javax.inject.Inject

/**
 * @package    SplashActivity.kt
 * @anthor     luan
 * @date       10:05 AM
 * @des        启动页
 */
class SplashActivity : BaseNetActivity<SplashPresenter>() {

    @Inject
    lateinit var userInfo: UserInfo

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, SplashActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getLayoutID(): Int = R.layout.activity_splash

    override fun initView(savedInstanceState: Bundle?) {
        mHandler.postDelayed({
            //判断用户是否登陆
            if (userInfo.isLogin()) {
                HomeActivity.launch(this)
            }else{
                LoginActivity.launch(this)
            }
        }, 3000)
        mPresenter.test()
    }

    override fun initData() {
    }

    override fun requestSuccess(bean: IBean, requestMode: BaseModel.RequestMode, requestTag: Any) {
    }

    override fun reRequest() {
    }

}

