package com.heid.frame.dialog

import android.content.Context
import com.heid.frame.R
import com.heid.frame.base.dialog.BaseDialog

/**
 * @package     com.heid.frame.dialog
 * @author      lucas
 * @date        2018/11/24
 * @des         通用小菊花
 */
class LoadingDialog(context: Context, var isCancel: Boolean = true) : BaseDialog(context) {
    override fun initView() {
        setCanceledOnTouchOutside(isCancel)
    }

    override fun onBackPressed() {
        if (isCancel)
            super.onBackPressed()
    }

    override fun getLayoutID(): Int? = R.layout.frame_dialog_loading
}