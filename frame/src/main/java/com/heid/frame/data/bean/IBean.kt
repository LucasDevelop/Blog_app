package com.heid.frame.data.bean

import java.io.Serializable

/**
 * @package     com.heid.frame.data.bean
 * @author      lucas
 * @date        2018/11/16
 * @des         数据原型
 */
open class IBean :Serializable{
    val status: Int? = null
    val msg: String? = null

    //判断list数据是否为空
    open fun isEmpty() = false
}