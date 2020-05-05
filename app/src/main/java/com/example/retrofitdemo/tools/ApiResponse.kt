package com.example.retrofitdemo.tools

/**
 * @author 李沐阳
 * @date：2020/4/22
 * @description:
 */
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error)
        }

        fun <T> create(body: T?): ApiResponse<T> {
            return if (body == null) {
                ApiEmptyResponse()
            } else {
                ApiSuccessResponse(body)
            }
        }
    }
}


class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

data class ApiErrorResponse<T>(val throwable: Throwable) : ApiResponse<T>()