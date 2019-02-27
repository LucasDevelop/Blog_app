package com.lucas.brush.utils

import com.heid.frame.config.FrameInitConfig


/**
 * @package    com.zhongde.tinglishi.utils
 * @anthor     lucas
 * @date       2018/12/21
 * @des
 */
object ImageUtil {

    //获取完整的图片地址
    fun getImgUrl(path: String): String {
        if (path.contains("http://") || path.contains("https://")) return path
        return FrameInitConfig.BASE_URL + path
    }


}