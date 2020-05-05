package com.example.retrofitdemo

import androidx.lifecycle.*
import com.example.retrofitdemo.tools.*

/**
 * @author 李沐阳
 * @date：2020/5/5
 * @description:
 */
class MainViewModel : ViewModel() {

    private val newsApi = getRetrofit().create(NewsApi::class.java)

    private val _newsLiveData = MediatorLiveData<ResultData<NewsBean>>()

    // 对外暴露的只是抽象的LiveData，防止外部随意更改数据
    val newsLiveData: LiveData<ResultData<NewsBean>>
        get() = _newsLiveData

    fun getNews() {
        val newsLiveData = viewModelScope.simpleRequestLiveData<NewsBean> {
            api { newsApi.getNews() }

            /**
             * 以下内容为可选实现
             */

            // 加载数据库缓存，直接返回 room 数据库的 LiveData
//            loadCache {
//                return@loadCache roomLiveData
//            }

            // 保存数据到 room 数据库
//            saveCache {
//            }
        }

        // 监听数据变化
        _newsLiveData.addSource(newsLiveData) {
            if (it.requestStatus == RequestStatus.COMPLETE) {
                _newsLiveData.removeSource(newsLiveData)
            }
            _newsLiveData.value = it
        }
    }
}