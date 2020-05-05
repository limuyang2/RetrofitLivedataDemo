package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.retrofitdemo.databinding.ActivityMainBinding
import com.example.retrofitdemo.tools.RequestStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.create
import retrofit2.http.GET
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

class MainActivity : AppCompatActivity() {

    // 利用系统扩展的代理，快速生成viewModel
    private val viewModel by viewModels<MainViewModel>()

    private lateinit var viewBinding: ActivityMainBinding

    private val mAdapter = MainRvAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.recyclerView.adapter = mAdapter

        // 定义LiveData
        viewModel.newsLiveData.observe(this, Observer {
            when (it.requestStatus) {
                RequestStatus.START -> {
                }
                RequestStatus.SUCCESS -> {
                    it.data?.let { newsBean ->
                        // 直接刷新界面
                        mAdapter.setList(newsBean.result)
                    }
                }
                RequestStatus.COMPLETE -> {
                    Toast.makeText(this, "网络请求完成了", Toast.LENGTH_SHORT).show()
                }
                RequestStatus.ERROR -> {
                    Toast.makeText(this, "网络出错了", Toast.LENGTH_SHORT).show()
                }
            }
        })


    }

    override fun onStart() {
        super.onStart()
        // 在你需要数据的地方，调用方法获取数据
        viewModel.getNews()
    }
}


