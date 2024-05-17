package com.example.teamproject_11.presentation.myvideo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamproject_11.databinding.ItemMyVideoBinding
import com.example.teamproject_11.presentation.home.model.HomeVideoModel

class MyVideoAdapter (var mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // 북마크된 아이템들을 저장하는 리스트
    var items = mutableListOf<HomeVideoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemMyVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // 이미지 로딩 라이브러리(Glide)를 사용해 썸네일 이미지를 로드한다.
        Glide.with(mContext)
            .load(items[position].imgThumbnail)
            .into((holder as ItemViewHolder).iv_thum_image)

        holder.tv_title.text = items[position].title
        holder.tv_datetime.text = items[position].dateTime

    }

    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * RecyclerView의 각 항목을 표현하는 ViewHolder 클래스입니다.
     */
    inner class ItemViewHolder(binding: ItemMyVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        var iv_thum_image: ImageView = binding.imageView
        var tv_title: TextView = binding.tvVideoName
        var tv_datetime: TextView = binding.tvVideoDate

    }

}
