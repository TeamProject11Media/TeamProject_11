package com.example.teamproject_11.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.teamproject_11.databinding.ItemProgressBinding
import com.example.teamproject_11.databinding.ItemSearchBinding
import com.example.teamproject_11.presentation.home.model.HomeVideoModel
import com.example.teamproject_11.presentation.main.DataType

const val ITEM = 0
const val PROGRESS = 1

class DetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemList: MutableList<HomeVideoModel> = mutableListOf()

    class MyViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeVideoModel) {
            binding.ivSearch.load(item.imgThumbnail) {
                crossfade(true)
            }
            binding.tvTitle.text = item.title
            binding.tvDate.text = showDate(item.dateTime!!)
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
                return MyViewHolder(binding)
            }

            else -> {
                val binding =
                    ItemProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MyViewProgressHolder(binding)
            }
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            holder.bind(itemList[position])
        } else {
        }
    }

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