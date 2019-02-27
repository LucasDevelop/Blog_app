package com.lucas.brush.main.personal.blogs.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.lucas.brush.R
import com.heid.frame.base.activity.BaseNetActivity
import com.heid.frame.data.api.BaseModel
import com.heid.frame.data.bean.IBean
import kotlinx.android.synthetic.main.activity_add_blog.*

/**
 * @package    AddBlogActivity.kt
 * @anthor     luan
 * @date       9:06 AM
 * @des        添加博客
 */
class AddBlogActivity : BaseNetActivity<AddBlogPresenter>(), View.OnClickListener {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, AddBlogActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getLayoutID(): Int = R.layout.activity_add_blog

    override fun initView(savedInstanceState: Bundle?) {
        setToolbar("添加博客", rightRes = "预览", rightClickListener = this,rightTextColor = R.color.white)
        arrayOf(v_save).setOnClickListener(this)
    }

    override fun initData() {
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.frame_toolbar_right -> {//预览

            }
            R.id.v_save -> {//保存
                val name = v_blog_name.text.toString().trim()
                val url = v_blog_url.text.toString().trim()
                mapOf(name.isEmpty() to "名称不能为空",
                        url.isEmpty() to "地址不能为空",
                        !url.contains("http") to "地址格式不正确").formCheck {
                    mPresenter.add(name, url)
                }
            }
        }
    }

    override fun requestSuccess(bean: IBean, requestMode: BaseModel.RequestMode, requestTag: Any) {
    }

    override fun reRequest() {
    }

}

