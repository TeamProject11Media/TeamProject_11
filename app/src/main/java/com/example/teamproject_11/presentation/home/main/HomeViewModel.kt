package com.example.teamproject_11.presentation.home.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamproject_11.network.RetroClient
import com.example.teamproject_11.data.remote.model.YouTubeResponse
import com.example.teamproject_11.presentation.main.DataType
import com.example.teamproject_11.presentation.home.model.HomeVideoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel() : ViewModel() {


    private val _videos = MutableLiveData<List<HomeVideoModel>>()
    val video: LiveData<List<HomeVideoModel>> = _videos

    private val _gameVideos = MutableLiveData<List<HomeVideoModel>>()
    val gameVideo: LiveData<List<HomeVideoModel>> = _gameVideos

    private val _musicVideos = MutableLiveData<List<HomeVideoModel>>()
    val musicVideo: LiveData<List<HomeVideoModel>> = _musicVideos

    private val _petVideos = MutableLiveData<List<HomeVideoModel>>()
    val petVideo: LiveData<List<HomeVideoModel>> = _petVideos

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val apiService = RetroClient.youtubeNetwork


    fun fetchPopularVideos() {
            val call = apiService.getVideoInfo(
                order = "mostPopular",
                regionCode = "KR",
                maxResult = 10
            )
            call.enqueue(object : Callback<YouTubeResponse> {
                override fun onResponse(
                    call: Call<YouTubeResponse>,
                    response: Response<YouTubeResponse>
                ) {
                    if (response.isSuccessful) {
                        val videoItems = response.body()?.items ?: emptyList()
                        val videoModels = videoItems.map { videoItem ->
                            HomeVideoModel(
                                id = videoItem.id,
                                imgThumbnail = videoItem.snippet?.thumbnails?.high?.url,
                                title = videoItem.snippet?.title,
                                dateTime = videoItem.snippet?.publishedAt,
                                description = videoItem.snippet?.description,
                                Type = DataType.MOST.viewType
                            )
                        }
                        _videos.postValue(videoModels)
                    } else {
                        _error.postValue("Error fetching videos")
                    }
                }

                override fun onFailure(call: Call<YouTubeResponse>, t: Throwable) {
                    _error.postValue("Error: ${t.message}")
                }
            })
        }

    fun fetchGameVideo() {
        val call = apiService.getVideoInfo(
            categoryId = "20",
            regionCode = "KR",
            maxResult = 10
        )
        call.enqueue(object : Callback<YouTubeResponse> {
            override fun onResponse(call: Call<YouTubeResponse>, response: Response<YouTubeResponse>) {
                if (response.isSuccessful) {
                    val videoItems = response.body()?.items ?: emptyList()
                    val videoModels = videoItems.map { videoItem ->
                        HomeVideoModel(
                            id = videoItem.id,
                            imgThumbnail = videoItem.snippet?.thumbnails?.high?.url,
                            title = videoItem.snippet?.title,
                            dateTime = videoItem.snippet?.publishedAt,
                            description = videoItem.snippet?.description,
                            Type = DataType.GAME.viewType
                        )
                    }
                    _gameVideos.postValue(videoModels)
                } else {
                    _error.postValue("Error fetching videos")
                }
            }
            override fun onFailure(call: Call<YouTubeResponse>, t: Throwable) {
                _error.postValue("Error: ${t.message}")
            }

        })
    }

    fun fetchMusicVideo() {
        val call = apiService.getVideoInfo(
            categoryId = "10",
            regionCode = "KR",
            maxResult = 10
        )
        call.enqueue(object : Callback<YouTubeResponse> {
            override fun onResponse(call: Call<YouTubeResponse>, response: Response<YouTubeResponse>) {
                if (response.isSuccessful) {
                    val videoItems = response.body()?.items ?: emptyList()
                    val videoModels = videoItems.map { videoItem ->
                        HomeVideoModel(
                            id = videoItem.id,
                            imgThumbnail = videoItem.snippet?.thumbnails?.high?.url,
                            title = videoItem.snippet?.title,
                            dateTime = videoItem.snippet?.publishedAt,
                            description = videoItem.snippet?.description,
                            Type = DataType.MUSIC.viewType
                        )
                    }
                    _musicVideos.postValue(videoModels)
                } else {
                    _error.postValue("Error fetching videos")
                }
            }
            override fun onFailure(call: Call<YouTubeResponse>, t: Throwable) {
                _error.postValue("Error: ${t.message}")
            }

        })
    }

    fun fetchPetVideo() {
        val call = apiService.getVideoInfo(
            categoryId = "15",
            regionCode = "KR",
            maxResult = 10
        )
        call.enqueue(object : Callback<YouTubeResponse> {
            override fun onResponse(call: Call<YouTubeResponse>, response: Response<YouTubeResponse>) {
                if (response.isSuccessful) {
                    val videoItems = response.body()?.items ?: emptyList()
                    val videoModels = videoItems.map { videoItem ->
                        HomeVideoModel(
                            id = videoItem.id,
                            imgThumbnail = videoItem.snippet?.thumbnails?.high?.url,
                            title = videoItem.snippet?.title,
                            dateTime = videoItem.snippet?.publishedAt,
                            description = videoItem.snippet?.description,
                            Type = DataType.MOVIE.viewType
                        )
                    }
                    _petVideos.postValue(videoModels)
                } else {
                    _error.postValue("Error fetching videos")
                }
            }
            override fun onFailure(call: Call<YouTubeResponse>, t: Throwable) {
                _error.postValue("Error: ${t.message}")
            }

        })
    }


}
