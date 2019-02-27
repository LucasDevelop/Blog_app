package com.heid.frame.base.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils
import com.heid.frame.R
import com.heid.frame.bus.AppBus
import com.heid.frame.dialog.LoadingDialog
import com.heid.frame.helper.CommentHelper
import com.heid.frame.mvp.view.IView
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * @package     com.heid.frame.base
 * @author      lucas
 * @date        2018/11/20
 * @des         activity底层基类--包含一些基本的功能,一般用于无网络请求无dagger注入的界面
 */
abstract class BaseActivity : RxAppCompatActivity(), IView, CommentHelper {

    var mToolBar: Toolbar? = null
    val mLoadingDialog: LoadingDialog by lazy { LoadingDialog(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        //竖屏
        if (isUseOrientation())
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        baseInit()
        initView(savedInstanceState)
        initData()
        initEvent()
    }

    open fun isUseOrientation(): Boolean =true

    //内部初始化
    private fun baseInit() {
        //填充布局
        if (getLayoutID() != 0)
            setContentView(getLayoutID())
        if (registerBus())
            AppBus.register(this)
        initFindView()
    }

    open fun initFindView() {
        //查找控件
        mToolBar = findViewById(R.id.frame_toolbar)
    }

    //视图初始化
    abstract fun initView(savedInstanceState: Bundle?)

    //数据初始化
    abstract fun initData()

    //事件初始化
    open fun initEvent() {}

    //布局id
    abstract fun getLayoutID(): Int

    //注册事件
    open fun registerBus() = false

    //Activity是否添加动画
    open fun toggleOverridePendingTransition() = true

    //Activity动画类型
    open fun getOverridePendingTransitionMode() = TransitionMode.RIGHT

    enum class TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE, FINISH
    }

    //显示加载框
    override fun showLoading() {
        if (!mLoadingDialog.isShowing)
            mLoadingDialog.show()
    }

    //隐藏加载框
    override fun hideLoading() {
        if (mLoadingDialog.isShowing)
            mLoadingDialog.dismiss()
    }

    /**
     * 设置toolbar
     * @param title 中间显示的文本
     * @param isBack 是否显示返回按钮
     * @param textSize 字体大小
     * @param textColor  字体的颜色
     * @param layoutParams 文字参数
     * @param rightRes 右边的资源
     * @param rightClickListener    右边资源的点击事件
     */
    fun setToolbar(
        title: String,
        isBack: Boolean = true,
        rightRes: Any = 0,
        rightTextColor: Int = 0,
        rightClickListener: View.OnClickListener? = null, @DrawableRes leftRes: Int = 0,
        bg:Int=R.color.colorPrimary,
        textSize: Float = 24f,
        textColor: Int = R.color.frame_white_color,
        layoutParams: Toolbar.LayoutParams = Toolbar.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            Gravity.CENTER
        )
    ) {
        if (mToolBar == null)
            return
        //背景颜色
        mToolBar!!.setBackgroundColor(resources.getColor(bg))
        if (isBack) {
            if (leftRes == 0) {
//                mToolBar?.setNavigationIcon(R.mipmap.frame_ic_back)
//                mToolBar?.navigationContentDescription = "返回"
                val textView = TextView(this)
                textView.id = R.id.frame_toolbar_back
                textView.text = "返回"
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                if (textColor != 0)
                    textView.setTextColor(resources.getColor(textColor))
                val backLayoutParams = Toolbar.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER_VERTICAL
                )
                backLayoutParams.setMargins(SizeUtils.dp2px(15f),0,0,0)
                mToolBar?.addView(textView, backLayoutParams)
                textView.setOnClickListener { finish() }
            } else {
                mToolBar?.setNavigationIcon(leftRes)
                mToolBar?.setNavigationOnClickListener {
                    finish()
                }
            }

        }
        if (!TextUtils.isEmpty(title)) {
            val textView = TextView(this)
            textView.id = R.id.frame_toolbar_title
            textView.text = title
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
            if (textColor != 0)
                textView.setTextColor(resources.getColor(textColor))
            mToolBar?.addView(textView, layoutParams)
        }
        if (rightClickListener == null) return
        val rightParams = Toolbar.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            Gravity.RIGHT
        )
        //判断是资源还是文本
        val dp15 = SizeUtils.dp2px(15f)
        if (rightRes is Int) {
            val rightImg = ImageView(this)
            rightImg.id = R.id.frame_toolbar_right
            rightImg.setPadding(dp15, 0, dp15, 0)
            rightImg.setImageResource(rightRes)
            rightImg.setOnClickListener(rightClickListener)
            mToolBar?.addView(rightImg, rightParams)
        }
        if (rightRes is String) {
            val textView = TextView(this)
            textView.id = R.id.frame_toolbar_right
            textView.gravity = Gravity.CENTER
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            if (rightTextColor != 0) {
                textView.setTextColor(resources.getColor(rightTextColor))
            } else {
                textView.setTextColor(resources.getColor(R.color.frame_title_color))
            }
            textView.setPadding(dp15, 0, dp15, 0)
            textView.text = rightRes
            textView.setOnClickListener(rightClickListener)
            mToolBar?.addView(textView, rightParams)
        }

    }

    //onActivityResult回调
    var onACResult:(requestCode: Int, resultCode: Int, data: Intent?)->Unit={ _, _, _ ->  }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onACResult(requestCode, resultCode, data)
    }


    override fun onDestroy() {
        super.onDestroy()
        if (registerBus())
            AppBus.unregister(this)
    }

    //初始化activity打开与关闭动画
    private fun initAnim() {
        if (toggleOverridePendingTransition()) {
            when (getOverridePendingTransitionMode()) {
                TransitionMode.LEFT -> overridePendingTransition(R.anim.frame_left_in, R.anim.frame_left_out)
                TransitionMode.RIGHT -> overridePendingTransition(R.anim.frame_right_in, R.anim.frame_right_out)
                TransitionMode.FINISH -> overridePendingTransition(R.anim.frame_right_in, R.anim.frame_right_finish)
                TransitionMode.TOP -> overridePendingTransition(R.anim.frame_top_in, R.anim.frame_top_out)
                TransitionMode.BOTTOM -> overridePendingTransition(R.anim.frame_bottom_in, R.anim.frame_bottom_out)
                TransitionMode.SCALE -> overridePendingTransition(R.anim.frame_scale_in, R.anim.frame_scale_out)
                TransitionMode.FADE -> overridePendingTransition(R.anim.frame_fade_in, R.anim.frame_fade_out)
            }
        }
    }

    override fun finish() {
        super.finish()
        if (toggleOverridePendingTransition()) {
            when (getOverridePendingTransitionMode()) {
                TransitionMode.LEFT -> overridePendingTransition(R.anim.frame_left_in, R.anim.frame_left_out)
                TransitionMode.RIGHT -> overridePendingTransition(R.anim.frame_right_in, R.anim.frame_right_out)
                TransitionMode.FINISH -> overridePendingTransition(R.anim.frame_right_in, R.anim.frame_right_finish)
                TransitionMode.TOP -> overridePendingTransition(R.anim.frame_top_in, R.anim.frame_top_out)
                TransitionMode.BOTTOM -> overridePendingTransition(R.anim.frame_bottom_in, R.anim.frame_bottom_out)
                TransitionMode.SCALE -> overridePendingTransition(R.anim.frame_scale_in, R.anim.frame_scale_out)
                TransitionMode.FADE -> overridePendingTransition(R.anim.frame_fade_in, R.anim.frame_fade_out)
            }
        }
    }
}