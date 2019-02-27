package com.zhongde.tinglishi

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken

/**
 * @package    com.zhongde.tinglishi
 * @anthor     luan
 * @date       2019/1/8
 * @des
 */
class CustomGsonAdapterFactory : TypeAdapterFactory {
    override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T>? {
        val rawType = type?.rawType as? Class<T>
        if (rawType != null) {
            //处理字符串
            if (rawType == String::class.java)
                return StringNullAdapter() as? TypeAdapter<T>
            //处理int
            if (rawType == Int::class.java) {
                return IntNullAdapter() as? TypeAdapter<T>
            }
        }


//        if (rawType != null && rawType.isAssignableFrom(List::class.java)) {
//
//        }

        return null
    }
}