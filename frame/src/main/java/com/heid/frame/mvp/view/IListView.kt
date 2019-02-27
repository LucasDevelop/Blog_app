package com.heid.frame.mvp.view

import com.heid.frame.data.bean.IBean

/**
 * @package     com.heid.frame.mvp.view
 * @author      lucas
 * @date        2018/11/24
 * @des
 */
interface IListView : ISwipeView {
    //加载更多失败
    fun showLoadMoreFailView()

    //加载更多成功
    fun showLoadMoreSuccessView()

    //加载更多错误
    fun showLoadMoreErrorView()
}