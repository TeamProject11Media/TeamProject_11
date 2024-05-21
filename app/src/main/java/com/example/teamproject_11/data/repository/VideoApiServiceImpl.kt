package com.example.teamproject_11.data.repository

import com.example.teamproject_11.data.model.YouTubeSearchResponse
import com.example.teamproject_11.data.remote.VideoApiService
import com.example.teamproject_11.domain.model.toEntity
import com.example.teamproject_11.domain.repository.YouTubeRepository

class VideoApiServiceImpl(
    private val videoApiService: VideoApiService
) : YouTubeRepository{
    override suspend fun getVideoInfo(
        apiKey: String,
        part: String,
        order: String,
        type: String,
        maxResult: Int,
        categoryId: String?,
        regionCode: String,
        channelId: String?,
        pageToken: String?,
    ) = videoApiService.getVideoInfo(apiKey, part, order, type, maxResult, categoryId, regionCode, channelId).toEntity()

    override suspend fun searchVideo(
        apiKey: String,
        part: String,
        type: String,
        maxResult: Int,
        regionCode: String,
        q: String?,
        pageToken: String?
    ): YouTubeSearchResponse = videoApiService.searchVideo(apiKey, part, type, maxResult, regionCode, q, pageToken)
}