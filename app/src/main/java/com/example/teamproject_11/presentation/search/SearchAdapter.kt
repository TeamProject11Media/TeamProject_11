package com.example.teamproject_11.presentation.search

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamproject_11.databinding.ItemSearchBinding
import com.example.teamproject_11.presentation.home.model.SearchVideoModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SearchAdapter(
    var items: List<SearchVideoModel>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onClick(view: View, position: Int)
    }

    class ViewHolder(
        private val binding: ItemSearchBinding,
        private val itemClickListener: OnItemClickListener?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(items: SearchVideoModel) {
            binding.apply {
                Glide.with(root.context)
                    .load(items.imgThumbnail)
                    .into(ivSearch)

                tvTitle.text = items.title ?: "No title available"

                //날짜 형식 깔끔하게 바꾸기
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                val date = LocalDateTime.parse(items.dateTime, formatter)
                tvDate.text = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) ?: "No date available"

                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener?.onClick(it, position)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(data: List<SearchVideoModel>) {
        this.items = data
        notifyDataSetChanged()
    }
}