package com.heid.frame.data.api

/**
 * @package     com.heid.frame.data.api
 * @author      lucas
 * @date        2018/11/19
 * @des         网络请求类，直接提供外部使用
 */
class FRequest {
    init {

    }

    //retrofit 的server
    lateinit var server:Any

    var func:(param:Any)->Unit = {}
}