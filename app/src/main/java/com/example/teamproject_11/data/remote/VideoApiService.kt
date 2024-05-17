package com.example.teamproject_11.data.remote


import com.example.teamproject_11.data.model.YouTubeResponse
import com.example.teamproject_11.network.RetroClient
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoApiService {
    @GET("videos")
    suspend fun getVideoInfo(
        @Query("key") apiKey: String = RetroClient.API_KEY,
        @Query("part") part: String = "snippet",
        @Query("chart") order: String = "mostPopular",
        @Query("type") type: String = "video",
        @Query("maxResults") maxResult: Int,
        @Query("videoCategoryId") categoryId: String? = null,
        @Query("regionCode") regionCode: String = "KR",
        @Query("channelId") channelId: String? = null,
        @Query("pageToken") pageToken: String? = null,
    ): YouTubeResponse
}