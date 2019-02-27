package com.zhongde.tinglishi

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

/**
 * @package    com.zhongde.tinglishi
 * @anthor     luan
 * @date       2019/1/8
 * @des        将字符串类型的字段null替换成"null"
 */
class StringNullAdapter:TypeAdapter<String>() {
    override fun write(out: JsonWriter, value: String?) {
        if (value==null){
            out.nullValue()
            return
        }
        out.value(value)
    }

    override fun read(reader: JsonReader): String {
        //将null替换成""
        if (reader.peek()==JsonToken.NULL){
            reader.nextNull()
            return "null"
        }
        return reader.nextString()
    }
}