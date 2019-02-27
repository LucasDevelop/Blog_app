package com.lucas.brush.main.auther.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.lucas.brush.R
import com.heid.frame.base.activity.BaseNetActivity
import com.heid.frame.data.api.BaseModel
import com.heid.frame.data.bean.IBean
import com.lucas.brush.main.auther.forget.ForgetActivity
import com.lucas.brush.main.auther.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * @package    LoginActivity.kt
 * @anthor     luan
 * @date       4:40 PM
 * @des        用户登陆
 */
class LoginActivity : BaseNetActivity<LoginPresenter>(),View.OnClickListener {
    companion object {
        fun launch(activity:Activity){
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        setToolbar("登陆",false)
        arrayOf(v_login,v_forget,v_register).setOnClickListener(this)
    }

    override fun initData() {
    }

    override fun onClick(v: View) {
        when (v) {
            v_login -> {
                //表单验证
                val username = v_username.text.toString().trim()
                val pwd = v_password.text.toString().trim()
                mapOf(
                        username.isEmpty() to "用户名不能为空!",
                        pwd.isEmpty() to "密码不能为空！"
                ).formCheck { mPresenter.login(username,pwd) }
            }
            v_forget -> ForgetActivity.launch(this)
            v_register-> RegisterActivity.launch(this)
        }
    }

    override fun getLayoutID(): Int = R.layout.activity_login

    override fun requestSuccess(bean: IBean, requestMode: BaseModel.RequestMode, requestTag: Any) {
    }

    override fun reRequest() {
    }

}

