package com.heid.frame.data.api

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.heid.frame.FrameApp
import com.heid.frame.config.FrameConfig
import com.heid.frame.config.FrameInitConfig
import com.zhongde.tinglishi.CustomGsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @package     com.heid.frame.data.api
 * @author      lucas
 * @date        2018/11/19
 * @des         网络层初始化
 */
@Module
class ApiModule {

    //retrofit初始化
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson) = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .baseUrl(FrameInitConfig.BASE_URL)
        .build()

    //OKHTTP初始化
    @Singleton
    @Provides
    fun provideOKhttp(cache: Cache): OkHttpClient {
        val builder = OkHttpClient.Builder()
        FrameInitConfig.interceptors.forEach {
            builder.addInterceptor(it)
        }
        builder.connectTimeout(FrameConfig.NET_CONN_TIMEOUT, TimeUnit.SECONDS)
        builder.readTimeout(FrameConfig.NET_READ_TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(FrameConfig.NET_WRITE_TIMEOUT, TimeUnit.SECONDS)
        builder.cache(cache)
        return builder.build()
    }

    //缓存
    @Singleton
    @Provides
    fun provideCache(frameApp: Application) = Cache(frameApp.cacheDir,FrameConfig.HTTP_CACHE_SIZE)

    //解析
    @Singleton
    @Provides
    fun provideGson() = GsonBuilder()
            .serializeNulls()//序列化null
            .setDateFormat("yyyy-MM-dd")//设置日期格式
            .setPrettyPrinting()//格式化输出
//            .registerTypeAdapterFactory(CustomGsonAdapterFactory())//自定义解析
            .create()
}