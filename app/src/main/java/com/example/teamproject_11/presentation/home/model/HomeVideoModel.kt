package com.example.teamproject_11.presentation.home.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.teamproject_11.data.remote.model.YouTubeVideo
import kotlinx.parcelize.Parcelize


data class HomeVideoResponse(
    val items: List<YouTubeVideo>
)


@Entity(tableName = "MyList")
@Parcelize
data class HomeVideoModel(
    @PrimaryKey
    val id: String,
    val imgThumbnail: String?,
    val title: String?,
    val dateTime: String?,
    val description: String?,
    val Type: Int,
) : Parcelable

