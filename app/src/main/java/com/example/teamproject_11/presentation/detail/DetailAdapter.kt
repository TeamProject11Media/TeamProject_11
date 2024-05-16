package com.example.teamproject_11.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.teamproject_11.databinding.ItemSearchBinding
import com.example.teamproject_11.presentation.home.model.HomeVideoModel

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.MyViewHolder>() {

    var itemList: List<HomeVideoModel> = listOf()

    class MyViewHolder(private val binding: ItemSearchBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(item: HomeVideoModel) {
                    binding.ivSearch.load(item.imgThumbnail) {
                        crossfade(true)
                    }
                    binding.tvTitle.text = item.title
                    binding.tvDate.text = item.dateTime
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    fun setItem(data: List<HomeVideoModel>) {
        this.itemList = data
        notifyDataSetChanged()
    }


}