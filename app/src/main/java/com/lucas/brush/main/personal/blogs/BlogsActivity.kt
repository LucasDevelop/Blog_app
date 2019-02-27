package com.lucas.brush.main.personal.blogs

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.lucas.brush.R
import com.heid.frame.base.activity.BaseRefreshListActivity
import com.heid.frame.data.api.BaseModel
import com.heid.frame.data.bean.IBean
import com.heid.frame.widget.SwipeItemLayout
import com.lucas.brush.main.personal.blogs.add.AddBlogActivity

/**
 * @package    BlogsActivity.kt
 * @anthor     luan
 * @date       8:58 AM
 * @des        博客管理
 */
class BlogsActivity : BaseRefreshListActivity<BlogsPresenter, BlogsBean.DataBean, BlogsAdapter>(), View.OnClickListener {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, BlogsActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getLayoutID(): Int = R.layout.activity_blogs

    override fun loadMoreListRequest(page: Int) {
    }

    override fun reRequest() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        setToolbar("博客管理", rightRes = R.mipmap.add, rightClickListener = this)
        mRecyclerView?.addOnItemTouchListener(SwipeItemLayout.OnSwipeItemTouchListener(this))
        mBaseAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.v_modify -> {
                }
                R.id.v_del -> mPresenter.delBlog((adapter.getItem(position) as BlogsBean.DataBean).id)
            }
        }
    }

    override fun initData() {
        mPresenter.getBlogList()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.frame_toolbar_right -> {//add
                AddBlogActivity.launch(this)
            }
            else -> {
            }
        }
    }

    override fun requestSuccess(bean: IBean, requestMode: BaseModel.RequestMode, requestTag: Any) {
        if (bean is BlogsBean)
            notifyAdapterStatus(bean.data, requestMode)
    }

}


