package com.example.retrofitdemo.tools

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * @author 李沐阳
 * @date：2020/4/22
 * @description:
 */
fun getRetrofit(): Retrofit {
    // 正常的构建 Retrofit ，没有区别

    val builder = OkHttpClient.Builder()

    return Retrofit.Builder()
        .baseUrl("https://api.apiopen.top")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(builder.build())
        .build()
}
