package com.example.teamproject_11.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamproject_11.databinding.ItemProgressBinding
import com.example.teamproject_11.databinding.ItemSearchBinding
import com.example.teamproject_11.presentation.detail.ITEM
import com.example.teamproject_11.presentation.detail.PROGRESS
import com.example.teamproject_11.presentation.home.model.HomeVideoModel
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
        fun bind(items: HomeVideoModel) {
            binding.apply {
                Glide.with(root.context)
                    .load(items.imgThumbnail)
                    .into(ivSearch)

                tvTitle.text = items.title ?: "No title available"
                binding.tvDate.text = showDate(items.dateTime!!)

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
        return when (itemList[position].id) {
            "Progress" -> PROGRESS
            else -> ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM -> {
                val binding =
                    ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ItemViewHolder(binding, itemClickListener)
            }

            else -> {
                val binding =
                    ItemProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MyViewProgressHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bind(itemList[position])
        } else {
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun setItem(data: List<HomeVideoModel>) {
        this.itemList = data.toMutableList()
        this.itemList.add(
            HomeVideoModel(
                "Progress",
                null,
                null,
                null,
                null,
                DataType.MOST.viewType
            )
        )
        notifyDataSetChanged()
    }
}

private fun showDate(date: String): String {
    val str = date.substring(0, 10)
    return str
}