package com.lucas.brush.main.auther.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.lucas.brush.R
import com.heid.frame.base.activity.BaseNetActivity
import com.heid.frame.data.api.BaseModel
import com.heid.frame.data.bean.IBean
import kotlinx.android.synthetic.main.activity_register.*

/**
 * @package    RegisterActivity.kt
 * @anthor     luan
 * @date       9:45 AM
 * @des        注册
 */
class RegisterActivity : BaseNetActivity<RegisterPresenter>(), View.OnClickListener {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, RegisterActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getLayoutID(): Int = R.layout.activity_register

    override fun initView(savedInstanceState: Bundle?) {
        setToolbar("注册账号")
        arrayOf(v_register).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            v_register -> {
                val username = v_username.text.toString().trim()
                val pwd = v_password.text.toString().trim()
                val pwd2 = v_password2.text.toString().trim()
                mapOf(
                        username.isEmpty() to "账号不能为空",
                        pwd.isEmpty() to "密码不能为空",
                        (pwd != pwd2) to "两次输入密码不一致",
                        (pwd.length < 6) to "密码长度最少6位"
                ).formCheck { mPresenter.register(username,pwd) }
            }
            else -> {
            }
        }
    }

    override fun initData() {
    }

    override fun requestSuccess(bean: IBean, requestMode: BaseModel.RequestMode, requestTag: Any) {
    }

    override fun reRequest() {
    }

}

