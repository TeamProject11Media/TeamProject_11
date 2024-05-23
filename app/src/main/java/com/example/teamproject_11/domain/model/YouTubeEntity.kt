package com.example.teamproject_11.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class YouTubeResponseEntity(
    val kind: String?,
    val etag: String?,
    val nextPageToken: String?,
    val prevPageToken: String?,
    val pageInfo: PageEntity?,
    val items: List<YouTubeVideoEntity>?,
)

data class PageEntity(
    val totalResults: Int?,
    val resultsPerPage: Int,
)

data class YouTubeVideoEntity(
    val kind: String?,
    val etag: String?,
    val id: String,
    val snippet: SnippetEntity?
)

data class SnippetEntity(
    val publishedAt: String?, //임시로 Date 타입에서 String으로?
    val channelId: String?,
    val title: String?,
    val description: String?,
    val thumbnails: ThumbnailsEntity?,
    val tags: List<String?>?,
    val categoryId: String?,
)

data class ThumbnailsEntity(
    val default: KeyEntity,
    val medium: KeyEntity,
    val high: KeyEntity,
)

data class KeyEntity(
    val url: String?,
    val width: Int?,
    val height: Int?,
)

@Entity(tableName = "search_videos")
data class SearchVideoEntity(
    @PrimaryKey
    val id: String,
    val imgThumbnail: String?,
    val title: String?,
    val dateTime: String?,
    val description: String?,
    val type: Int
)