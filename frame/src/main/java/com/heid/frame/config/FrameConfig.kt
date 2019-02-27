package com.heid.frame.config

/**
 * @package     com.heid.frame.config
 * @author      lucas
 * @date        2018/11/19
 * @des         框架常量配置
 */
object FrameConfig {
    //网络连接时间
    const val NET_CONN_TIMEOUT = 60L
    //网络读取超时时间
    const val NET_READ_TIMEOUT = 60L
    //网络上传超时时间
    const val NET_WRITE_TIMEOUT = 60L
    //请求缓存大小
    const val HTTP_CACHE_SIZE = 1024 * 1024 * 30L

    //分页
    object Page {
        //初始页码
        const val START_INDEX = 1
        //每页数据量
        const val PAGE_COUNT = 20
    }

    //网络请求相关
    object Net {
        //请求成功码
        const val REQUEST_SUCCESS = 200
        //token过期码
        const val TOKEN_OVERDUE = 1000
    }
}