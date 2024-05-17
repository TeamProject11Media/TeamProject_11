package com.example.teamproject_11.domain.repository

import com.example.teamproject_11.data.model.YouTubeResponse
import com.example.teamproject_11.domain.model.YouTubeResponseEntity
import com.example.teamproject_11.network.RetroClient
import retrofit2.http.Query

interface YouTubeRepository {

    suspend fun getVideoInfo(
        apiKey: String = RetroClient.API_KEY,
        part: String = "snippet",
        order: String = "mostPopular",
        type: String = "video",
        maxResult: Int,
        categoryId: String? = null,
        regionCode: String = "KR",
        channelId: String? = null
    ): YouTubeResponseEntity
}