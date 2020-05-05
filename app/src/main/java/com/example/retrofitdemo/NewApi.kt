package com.example.retrofitdemo

import retrofit2.http.GET

/**
 * @author 李沐阳
 * @date：2020/5/6
 * @description:
 */

interface NewsApi {
    /**
     * 接口需要加上 [suspend] ！
     * 返回值，直接就是你的数据类型，不需要再包装其他的东西了，超级简介
     */
    @GET("/getWangYiNews")
    suspend fun getNews(): NewsBean
}