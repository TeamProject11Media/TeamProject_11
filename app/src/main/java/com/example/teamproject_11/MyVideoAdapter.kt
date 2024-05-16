package com.example.teamproject_11

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.teamproject_11.databinding.ItemMyVideoBinding
import com.example.teamproject_11.home.adapter.GameViewAdapter
import com.example.teamproject_11.home.data.HomeVideoModel

class MyVideoAdapter(private val mContext: Context) : RecyclerView.Adapter<MyVideoAdapter.MyViewHolder>(){
    private var listener: MyVideoAdapter.OnItemClickListener? = null
    var itemList: List<HomeVideoModel> = listOf()
    var myVideoitems = mutableListOf<HomeVideoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMyVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]

        Glide.with(mContext)
            .load(currentItem.imgThumbnail)
            .into(holder.img)

        holder.bind(itemList[position])
        holder.itemView.setOnClickListener {
            listener?.onItemClick(itemList[position])
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    interface OnItemClickListener {
        fun onItemClick(videoModel: HomeVideoModel)

    }

    inner class MyViewHolder (private val binding: ItemMyVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

            val img: ImageView = binding.imageView
        fun bind(item: HomeVideoModel) {
            binding.imageView.load(item.imgThumbnail) {
                crossfade(true)
            }

            itemView.setOnClickListener {
                listener?.onItemClick(item)
            }
        }
    }
}