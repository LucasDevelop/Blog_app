package com.lucas.brush.data

import com.heid.frame.data.bean.IBean
import com.lucas.brush.data.bean.LoginBean
import com.lucas.brush.data.bean.UserBean
import com.lucas.brush.main.personal.blogs.BlogsBean
import io.reactivex.Observable
import retrofit2.http.*


/**
 * @package     com.heid.frame20181119.data
 * @author      lucas
 * @date        2018/11/24
 * @des
 */
interface ApiServer {

    //登陆
    @FormUrlEncoded
    @POST("/blog/user/login")
    fun login(@Field("name") name: String, @Field("password") password: String): Observable<LoginBean>

    //注册
    @FormUrlEncoded
    @POST("/blog/user/register")
    fun register(@Field("name") name: String, @Field("password") password: String): Observable<IBean>

    //忘记密码
    @FormUrlEncoded
    @POST("/blog/user/forget")
    fun forget(@Field("name") name: String, @Field("newPassword") password: String): Observable<IBean>

    //获取博客列表
    @GET("/blog/blog/findBlogByUserId")
    fun getBlogList(@Query("userId") id: Int): Observable<BlogsBean>

    //获取所有博客列表
    @GET("/blog/blog/getAllBlog")
    fun getAllBlog():Observable<BlogsBean>

    //添加博客
    @Headers("needToken:true")
    @FormUrlEncoded
    @POST("/blog/blog/add")
    fun addBlog(@Field("userId") userId: Int, @Field("blogName") blogName: String, @Field("blogUrl") blogUrl: String): Observable<IBean>

    //删除博客
    @Headers("needToken:true")
    @FormUrlEncoded
    @POST("/blog/blog/del")
    fun delBlog(@Field("id") id: Int): Observable<IBean>

    //增加任务IP
    @Headers("needToken:true")
    @FormUrlEncoded
    @POST("/blog/ip/add")
    fun addIP(@Field("ipAddr") ipAddr:String):Observable<IBean>
}