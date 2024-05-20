package com.example.teamproject_11.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamproject_11.databinding.ItemSearchBinding
import com.example.teamproject_11.presentation.home.model.SearchVideoModel

class SearchAdapter(
    private var items: List<SearchVideoModel>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onClick(view: View, position: Int)
    }

    inner class ViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(items: SearchVideoModel) {
            binding.apply {
                Glide.with(root.context)
                    .load(items.imgThumbnail)
                    .into(ivSearch)

                tvTitle.text = items.title ?: "No title available"
                tvDate.text = items.dateTime ?: "No date available"

                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener.onClick(it, position)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
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