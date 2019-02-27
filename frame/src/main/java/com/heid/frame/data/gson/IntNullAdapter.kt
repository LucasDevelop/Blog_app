package com.zhongde.tinglishi

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

/**
 * @package    com.zhongde.tinglishi
 * @anthor     luan
 * @date       2019/1/8
 * @des        将Int类型的字段的"" null 以及其他类型替换成-1
 */
class IntNullAdapter : TypeAdapter<Int>() {
    override fun write(out: JsonWriter, value: Int?) {
        out.value(value)
    }

    override fun read(reader: JsonReader): Int {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return -1
        }
        try {
            reader.nextInt()
            return reader.nextInt()
        } catch (e: Exception) {
//            e.printStackTrace()
        }
        reader.skipValue()
        return -1
    }
}