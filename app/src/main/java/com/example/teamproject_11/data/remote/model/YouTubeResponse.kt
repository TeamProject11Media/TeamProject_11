package com.example.teamproject_11.data.remote.model

import com.google.gson.annotations.SerializedName

//유튜브 API 데이터 전송 형태
data class YouTubeResponse (
    @SerializedName("kind") val kind : String?,
    @SerializedName("etag") val etag : String?,
    @SerializedName("nextPageToken") val nextPageToken : String?,
    @SerializedName("prevPageToken") val prevPageToken : String?,
    @SerializedName("pageInfo") val pageInfo : Page?,
    @SerializedName("items") val items : List<YouTubeVideo>?,
)

//유튜브 전송 데이터 속성 중 PageInfo 오브젝트 클래스
data class Page(
    val totalResults: Int?,
    val resultsPerPage: Int,
)

//유튜브 전송 데이터 솓성 중 items에 해당하는 오브젝트 클래스

data class YouTubeVideo(
    val kind: String?,
    val etag: String?,
    val id: String,
    val snippet: Snippet?
)

//items의 속성 중 snippet에 해당하는 오브젝트 클래스, snippet은 해당 item의 업로드 시간 및 제목, 영상 설명 등의 속성을 가지고 있음
data class Snippet(
    val publishedAt: String?, //임시로 Date 타입에서 String으로 바꿨습니다
    val channelId : String?,
    val title : String?,
    val description : String?,
    val thumbnails : Thumbnails?,
    val tags: List<String?>?,
    val categoryId: String?,
    )

//snippet의 속성 중 썸네일에 해당하는 오브젝트 클래스, 해당 클래스는 Key라는 오브젝트 속성을 가지고 Key에 이미지 url 및 너비 길이 정보가 있음

data class Thumbnails(
    @SerializedName("default")
    val default: Key,
    @SerializedName("medium")
    val medium: Key,
    @SerializedName("high")
    val high: Key
)

data class Key(
    val url : String?,
    val width: Int?,
    val height: Int?,
)

//더미 데이터

val dummyData = listOf(
    YouTubeVideo(null, null, "1", Snippet("2023-05-11", null, "ABIGAIL","Detail Summary", Thumbnails(
        Key("1",0,0), Key("1",0,0), Key("1",0,0)
    ),null,"MOVIE")
    ),
    YouTubeVideo(null, null, "2", Snippet("2020-03-14", null, "ACCIDENTIAL TAXAN","Detail Summary", Thumbnails(
        Key("2",0,0), Key("2",0,0), Key("2",0,0)
    ),null,"MOVIE")
    ),
    YouTubeVideo(null, null, "3", Snippet("2003-11-21", null, "ALL SAINT DAY","Detail Summary", Thumbnails(
        Key("3",0,0), Key("3",0,0), Key("3",0,0)
    ),null,"MOVIE")
    ),
    YouTubeVideo(null, null, "4", Snippet("2011-07-12", null, "NILPHAS","Detail Summary", Thumbnails(
        Key("4",0,0), Key("4",0,0), Key("4",0,0)
    ),null,"MOVIE")
    ),
    YouTubeVideo(null, null, "5", Snippet("2018-08-13", null, "AMERICAN DREAMER","Detail Summary", Thumbnails(
        Key("5",0,0), Key("5",0,0), Key("5",0,0)
    ),null,"MOVIE")
    ),
    YouTubeVideo(null, null, "6", Snippet("2019-09-12", null, "AMERICAN SOCIETY","Detail Summary", Thumbnails(
        Key("6",0,0), Key("6",0,0), Key("6",0,0)
    ),null,"MOVIE")
    ),
    YouTubeVideo(null, null, "7", Snippet("2022-12-11", null, "AMERICAN STAR","Detail Summary", Thumbnails(
        Key("7",0,0), Key("7",0,0), Key("7",0,0)
    ),null,"MOVIE")
    ),
    YouTubeVideo(null, null, "8", Snippet("2010-07-18", null, "ARGYLLE","Detail Summary", Thumbnails(
        Key("8",0,0), Key("8",0,0), Key("8",0,0)
    ),null,"MOVIE")
    ),
    YouTubeVideo(null, null, "9", Snippet("2016-10-31", null, "ATLAS","Detail Summary", Thumbnails(
        Key("9",0,0), Key("9",0,0), Key("9",0,0)
    ),null,"MOVIE")
    ),
)





