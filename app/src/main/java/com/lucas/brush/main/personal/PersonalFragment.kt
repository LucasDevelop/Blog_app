package com.lucas.brush.main.personal

import android.os.Bundle
import android.view.View
import com.lucas.brush.R
import com.heid.frame.base.fragment.BaseNetFragment
import com.heid.frame.data.api.BaseModel
import com.heid.frame.data.bean.IBean
import com.lucas.brush.main.personal.blogs.BlogsActivity
import kotlinx.android.synthetic.main.fragment_personal.*

/**
 * @package    PersonalFragment.kt
 * @anthor     luan
 * @date       4:23 PM
 * @des        个人中心
 */
class PersonalFragment : BaseNetFragment<PersonalPresenter>(),View.OnClickListener {

    override fun getLayoutID(): Int = R.layout.fragment_personal

    override fun initView(savedInstanceState: Bundle?) {
        arrayOf(v_blogs).setOnClickListener(this)
    }

    override fun initData() {
    }

    override fun onClick(v: View) {
        when (v) {
            v_blogs -> {//博客管理
                BlogsActivity.launch(mActivity)
            }
            else -> {
            }
        }
    }

    override fun requestSuccess(bean: IBean, requestMode: BaseModel.RequestMode, requestTag: Any) {
    }

    override fun reRequest() {
    }

}

