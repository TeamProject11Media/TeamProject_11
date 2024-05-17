package com.example.teamproject_11.domain.repository

import com.example.teamproject_11.domain.model.YouTubeResponseEntity
import com.example.teamproject_11.network.RetroClient

interface YouTubeRepository {

    suspend fun getVideoInfo(
        apiKey: String = RetroClient.API_KEY,
        part: String = "snippet",
        order: String = "mostPopular",
        type: String = "video",
        maxResult: Int,
        categoryId: String? = null,
        regionCode: String = "KR",
        channelId: String? = null,
        pageToken: String?,
    ): YouTubeResponseEntity

    suspend fun searchVideo(
        apiKey: String,
        part: String,
        type: String,
        maxResult: Int,
        regionCode: String,
        q: String?
    ): YouTubeResponseEntity
}