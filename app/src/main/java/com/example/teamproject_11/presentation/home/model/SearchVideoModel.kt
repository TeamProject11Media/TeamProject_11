package com.example.teamproject_11.presentation.home.model

import androidx.room.PrimaryKey

data class SearchVideoModel(
    @PrimaryKey
    val id: String?,
    val imgThumbnail: String?,
    val title: String?,
    val dateTime: String?,
    val description: String?,
    val type: Int
)