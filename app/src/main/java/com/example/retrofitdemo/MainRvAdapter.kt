package com.example.retrofitdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.retrofitdemo.databinding.ItemNewsBinding

/**
 * @author 李沐阳
 * @date：2020/4/22
 * @description:
 */

class MainRvAdapter : RecyclerView.Adapter<MainRvAdapter.VH>() {
    class VH(val viewBinding: ItemNewsBinding) : RecyclerView.ViewHolder(viewBinding.root)

    private val list: MutableList<NewsBean.Result> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val data = list[position]

        holder.viewBinding.apply {
            Glide.with(ivPic)
                .load(data.image)
                .transform(CenterCrop(), RoundedCorners(10))
                .into(ivPic)

            tvTitle.text = data.title
            tvTime.text = data.passtime
        }
    }

    fun setList(newList: List<NewsBean.Result>) {
        // adapter 设置数据
        this.list.clear()
        this.list.addAll(newList)
        notifyDataSetChanged()
    }
}