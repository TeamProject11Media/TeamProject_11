package com.example.teamproject_11.domain.model


data class YouTubeResponseEntity (
    val kind : String?,
    val etag : String?,
    val nextPageToken : String?,
    val prevPageToken : String?,
    val pageInfo : PageEntity?,
    val items : List<YouTubeVideoEntity>?,
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

data class YouTubeVideoSearchIdEntity(
    val kind: String?,
    val videoId: String?,
    val channelId: String?,
    val playlistId: String?
)

data class SnippetEntity(
    val publishedAt: String?, //임시로 Date 타입에서 String으로 바꿨습니다
    val channelId : String?,
    val title : String?,
    val description : String?,
    val thumbnails : ThumbnailsEntity?,
    val tags: List<String?>?,
    val categoryId: String?,
)



data class ThumbnailsEntity(
    val default: KeyEntity,
    val medium: KeyEntity,
    val high: KeyEntity,
)

data class KeyEntity(
    val url : String?,
    val width: Int?,
    val height: Int?,
)