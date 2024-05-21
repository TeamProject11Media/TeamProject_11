package com.example.teamproject_11.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamproject_11.databinding.ItemProgressBinding
import com.example.teamproject_11.databinding.ItemSearchBinding
import com.example.teamproject_11.presentation.detail.DetailAdapter
import com.example.teamproject_11.presentation.detail.ITEM
import com.example.teamproject_11.presentation.detail.PROGRESS
import com.example.teamproject_11.presentation.home.model.HomeVideoModel
import com.example.teamproject_11.presentation.home.model.SearchVideoModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.example.teamproject_11.presentation.main.DataType


class SearchAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var itemList: MutableList<HomeVideoModel> = mutableListOf()

    interface OnItemClickListener {
        fun onClick(data: HomeVideoModel)
    }

    class ItemViewHolder(
        private val binding: ItemSearchBinding,
        private val itemClickListener: OnItemClickListener?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(items: HomeVideoModel) {
            binding.apply {
                Glide.with(root.context)
                    .load(items.imgThumbnail)
                    .into(ivSearch)

                tvTitle.text = items.title ?: "No title available"

                //날짜 형식 깔끔하게 바꾸기
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                val date = LocalDateTime.parse(items.dateTime, formatter)
                tvDate.text = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    ?: "No date available"

                root.setOnClickListener {
                    itemClickListener?.onClick(items)
                }
            }
        }
    }
    class MyViewProgressHolder(private val binding: ItemProgressBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun getItemViewType(position: Int): Int {
        return when(itemList[position].id){
            "Progress" -> PROGRESS
            else -> ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM -> {val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ItemViewHolder(binding, itemClickListener)
            }
            else -> {val binding = ItemProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MyViewProgressHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ItemViewHolder){
            holder.bind(itemList[position])
        }
        else{}
    }

    override fun getItemCount(): Int = itemList.size



    fun setItem(data: List<HomeVideoModel>) {
        this.itemList = data.toMutableList()
        this.itemList.add(HomeVideoModel("Progress", null,null,null, null, DataType.MOST.viewType))
//        notifyDataSetChanged()
    }
}