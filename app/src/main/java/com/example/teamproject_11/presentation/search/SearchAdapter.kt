package com.example.teamproject_11.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamproject_11.R
import com.example.teamproject_11.data.remote.model.YouTubeResponse

class SearchAdapter(
    val items: List<YouTubeResponse>,
    private var itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onClick(view: View, position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)

        fun bind(item: YouTubeResponse) {
            itemView.apply {
                Glide.with(context)
                    .load(item.items?.firstOrNull()?.snippet?.thumbnails)
                    .into(this as ImageView)

                tvTitle.text = item.items?.firstOrNull()?.snippet?.title ?: "No title available"
                tvDate.text = item.items?.firstOrNull()?.snippet?.publishedAt ?: "No date available"

                setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener.onClick(it, position)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}