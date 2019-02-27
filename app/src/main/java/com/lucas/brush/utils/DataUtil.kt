package com.lucas.brush.utils

import com.heid.frame.data.bean.IBean

/**
 * @package     com.zhongde.tinglishi.utils
 * @author      lucas
 * @date        2018/12/6
 * @des         假数据工具
 */
class DataUtil {
    companion object {
        fun getIBeanData(size: Int) = List(size) { IBean() }
    }
}