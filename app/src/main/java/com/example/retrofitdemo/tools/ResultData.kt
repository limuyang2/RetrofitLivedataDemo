package com.example.retrofitdemo.tools


data class ResultData<T>(val requestStatus: RequestStatus,
                         val data: T?,
                         val isCache: Boolean = false,
                         val error: Throwable? = null,
                         val tag: Any? = null) {
    companion object {

        fun <T> start(): ResultData<T> {
            return ResultData(
                RequestStatus.START,
                null
            )
        }

        fun <T> success(data: T?, isCache: Boolean = false): ResultData<T> {
            return ResultData(
                RequestStatus.SUCCESS,
                data,
                isCache
            )
        }

        fun <T> complete(data: T?): ResultData<T> {
            return ResultData(
                RequestStatus.COMPLETE,
                data,
                false
            )
        }

        fun <T> error(error: Throwable?): ResultData<T> {
            return ResultData(
                RequestStatus.ERROR,
                null,
                false,
                error
            )
        }

    }
}

