package com.example.teamproject_11.presentation.myvideo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.teamproject_11.databinding.ItemMyVideoBinding
import com.example.teamproject_11.presentation.home.model.HomeVideoModel


interface OnItemClick{
    fun onItemClick(item: HomeVideoModel)
}
class MyVideoAdapter(private val data : List<HomeVideoModel>, private val onItemClick: OnItemClick) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class MyVideoViewHolder(private val binding: ItemMyVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeVideoModel){
            binding.imageView.load(item.imgThumbnail) {
                crossfade(true)
            }
            binding.imageView.clipToOutline = true
            binding.tvVideoDate.text = item.dateTime
            binding.tvVideoName.text = item.title
            binding.myVideoContainer.setOnClickListener {
                onItemClick.onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       val binding = ItemMyVideoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       return MyVideoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as MyVideoViewHolder
        holder.bind(data[position])
    }


}