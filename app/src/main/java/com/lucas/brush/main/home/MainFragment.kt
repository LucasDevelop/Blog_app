package com.lucas.brush.main.home

import android.os.Bundle
import com.lucas.brush.R
import com.heid.frame.base.fragment.BaseNetFragment
import com.heid.frame.data.api.BaseModel
import com.heid.frame.data.bean.IBean

/**
 * @package    MainFragment.kt
 * @anthor     luan
 * @date       4:25 PM
 * @des        主页
 */
class MainFragment : BaseNetFragment<MainPresenter>() {

    override fun getLayoutID(): Int = R.layout.fragment_main

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }

    override fun requestSuccess(bean: IBean, requestMode: BaseModel.RequestMode, requestTag: Any) {
    }

    override fun reRequest() {
    }

}

