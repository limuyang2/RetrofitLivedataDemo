package com.example.retrofitdemo.tools

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * @author 李沐阳
 * @date：2020/5/6
 * @description:
 */

fun <T> MediatorLiveData<ResultData<T>>.addSource(liveData: LiveData<ResultData<T>>) {
    this.addSource(liveData) {
        if (it.requestStatus == RequestStatus.COMPLETE) {
            this.removeSource(liveData)
        }
        this.value = it
    }
}