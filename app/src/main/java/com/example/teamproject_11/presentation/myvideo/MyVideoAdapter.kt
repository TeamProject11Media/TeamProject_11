package com.example.teamproject_11.presentation.myvideo

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.teamproject_11.databinding.ItemMyVideoBinding
import com.example.teamproject_11.presentation.home.model.HomeVideoModel
import com.example.teamproject_11.presentation.search.SearchAdapter


interface OnItemClick{
    fun onItemClick(item: HomeVideoModel)
    fun onItemClickToDelete(item: HomeVideoModel)
}
class MyVideoAdapter(private val data : List<HomeVideoModel>, private val onItemClick: OnItemClick) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class MyVideoViewHolder(private val binding: ItemMyVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: HomeVideoModel){
            binding.imageView.load(item.imgThumbnail) {
                crossfade(true)
            }
            binding.imageView.clipToOutline = true
            binding.tvVideoDate.text = showDate(item.dateTime!!)

            //String -> date타입으로 바꾸고 포맷형식 바꾸기
//            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
//            val date = LocalDateTime.parse(item.dateTime, formatter)
//
//            binding.tvVideoDate.text = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

            binding.tvVideoName.text = item.title
            binding.myVideoContainer.setOnClickListener {
                if(fragmentMode == 0)
                onItemClick.onItemClick(item)
                else {
                    binding.myVideoContainer.setBackgroundColor(Color.rgb(255,0,0))
                    onItemClick.onItemClickToDelete(item)
                }
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as MyVideoViewHolder
        holder.bind(data[position])
    }

}

private fun showDate(date: String) : String{
    val str = date.substring(0,10)
    return str
}