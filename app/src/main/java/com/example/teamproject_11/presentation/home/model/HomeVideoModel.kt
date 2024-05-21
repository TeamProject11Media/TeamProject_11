package com.example.teamproject_11.presentation.home.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

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