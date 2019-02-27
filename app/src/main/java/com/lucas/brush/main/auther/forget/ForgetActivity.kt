package com.lucas.brush.main.auther.forget

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.lucas.brush.R
import com.heid.frame.base.activity.BaseNetActivity
import com.heid.frame.data.api.BaseModel
import com.heid.frame.data.bean.IBean
import kotlinx.android.synthetic.main.activity_forget.*

/**
 * @package    ForgetActivity.kt
 * @anthor     luan
 * @date       10:11 AM
 * @des        忘记密码
 */
class ForgetActivity : BaseNetActivity<ForgetPresenter>(), View.OnClickListener {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, ForgetActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getLayoutID(): Int = R.layout.activity_forget

    override fun initView(savedInstanceState: Bundle?) {
        setToolbar("忘记密码")
        arrayOf(v_submit).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            v_submit -> {
                val name = v_username.text.toString().trim()
                val pwd = v_password.text.toString().trim()
                mapOf(
                        name.isEmpty() to "账号不能为空",
                        pwd.isEmpty() to "密码不能为空"
                ).formCheck { mPresenter.forget(name, pwd) }
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

