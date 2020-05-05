package com.example.retrofitdemo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * @author 李沐阳
 * @date：2020/4/23
 * @description:
 */
@JsonClass(generateAdapter = true)
data class NewsBean(
    @Json(name = "code")
    val code: Int = 0, // 200
    @Json(name = "message")
    val message: String = "", // 成功!
    @Json(name = "result")
    val result: List<Result> = listOf()
) {
    @JsonClass(generateAdapter = true)
    data class Result(
        @Json(name = "image")
        val image: String = "", // http://cms-bucket.ws.126.net/2020/0422/02f9aad4p00q961tg00o5c000s600e3c.png?imageView&thumbnail=140y88&quality=85
        @Json(name = "passtime")
        val passtime: String = "", // 2020-04-22 10:00:33
        @Json(name = "path")
        val path: String = "", // https://news.163.com/20/0422/09/FAQCS9NS0001899O.html
        @Json(name = "title")
        val title: String = "" // 专家:疫情爆发后湖北官员水平比西方领导人平均数高
    )
}