package com.heid.frame.base.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.heid.frame.R
import com.heid.frame.config.FrameConfig
import com.heid.frame.data.api.BaseModel
import com.heid.frame.data.bean.IBean
import com.heid.frame.mvp.presenter.IPresenter
import com.heid.frame.mvp.view.IRequestView
import com.heid.frame.mvp.view.ISwipeView
import java.lang.RuntimeException
import java.util.ArrayList
import javax.inject.Inject

/**
 * @package     com.heid.frame.base.fragment
 * @author      lucas
 * @date        2018/11/26
 * @des
 */
abstract class BaseRefreshListFragment<P : IPresenter<*>, QA : BaseQuickAdapter<AB, BaseViewHolder>, AB> :
    BaseRefreshFragment<P>(){
    var mRecyclerView: RecyclerView? = null
    var currentPage = FrameConfig.Page.START_INDEX//初始页码
    var pageCount = FrameConfig.Page.PAGE_COUNT//每页数据量
    lateinit var mListEmptyView: View//recyclerView没有数据时显示的布局

    //如果这里报空指针请检查adapter的构造是否添加了@inject
    @Inject
    lateinit var mBaseAdapter: QA

    override fun initFindView() {
        super.initFindView()
        mRecyclerView = findView(R.id.frame_recycleView)
        if (mRecyclerView == null) {
            throw RuntimeException("布局中必须有RecyclerView，并且RecyclerView中的ID为frame_recycleView")
        }
        mRecyclerView?.apply {
            layoutManager = setRecyclerViewManager()
            adapter = mBaseAdapter
        }
        mListEmptyView = LayoutInflater.from(context)
            .inflate(R.layout.frame_view_pager_no_data, mRecyclerView!!.parent as ViewGroup, false)
    }

    //adapter状态切换
    fun notifyAdapterStatus(data: List<AB>?, loadMode: BaseModel.RequestMode) {
        if (loadMode === BaseModel.RequestMode.LOAD_MODE) {
            if (data == null) {
                mBaseAdapter.loadMoreEnd(false)
            } else {
                currentPage++
                mBaseAdapter.addData(data)
                if (data.size < pageCount) {
                    mBaseAdapter.loadMoreEnd(false)
                } else
                    mBaseAdapter.loadMoreComplete()
            }
        } else {
            if (data == null || data.isEmpty()) {
                mBaseAdapter.setNewData(ArrayList<AB>())
                mBaseAdapter.emptyView = mListEmptyView
                return
            }
            currentPage = 1
            if (data.size == pageCount) {
                mBaseAdapter.setOnLoadMoreListener(mRequestLoadMoreListener, mRecyclerView)
                currentPage++
            } else {
                mBaseAdapter.setOnLoadMoreListener(null, mRecyclerView)
            }
            mBaseAdapter.setNewData(data)
        }
    }

    //设置recyclerView的manager
    open fun setRecyclerViewManager() = LinearLayoutManager(context)

    //加载更多监听
    private val mRequestLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener {
        if (currentPage > 1) {
            loadMoreListRequest(currentPage)
        } else {
            mBaseAdapter.setEnableLoadMore(false)
        }
    }

    override fun onRefresh() {
        mBaseAdapter.loadMoreEnd(false)
    }

    //重置刷新
    override fun resetRefresh() {
        super.resetRefresh()
        if (!mBaseAdapter.isLoadMoreEnable)
            mBaseAdapter.setEnableLoadMore(true)
    }

    fun setLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }

    //加载更多时要发送的请求
    abstract fun loadMoreListRequest(page: Int)
}