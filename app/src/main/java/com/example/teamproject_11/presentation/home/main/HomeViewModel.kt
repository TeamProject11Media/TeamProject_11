package com.example.teamproject_11.presentation.home.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.teamproject_11.network.RetroClient
import com.example.teamproject_11.data.repository.VideoApiServiceImpl
import com.example.teamproject_11.domain.repository.YouTubeRepository
import com.example.teamproject_11.presentation.main.DataType
import com.example.teamproject_11.presentation.home.model.HomeVideoModel
import com.example.teamproject_11.presentation.home.model.SearchVideoModel
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.HttpException


class HomeViewModel(
    private val repository: YouTubeRepository
) : ViewModel() {


    private val _videos = MutableLiveData<List<HomeVideoModel>?>()
    val video: LiveData<List<HomeVideoModel>?> = _videos

    private val _gameVideos = MutableLiveData<List<HomeVideoModel>>()
    val gameVideo: LiveData<List<HomeVideoModel>> = _gameVideos

    private val _musicVideos = MutableLiveData<List<HomeVideoModel>>()
    val musicVideo: LiveData<List<HomeVideoModel>> = _musicVideos

    private val _petVideos = MutableLiveData<List<HomeVideoModel>>()
    val petVideo: LiveData<List<HomeVideoModel>> = _petVideos

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _searchVideos = MutableLiveData<List<SearchVideoModel>?>()
    val searchVideo: LiveData<List<SearchVideoModel>?> = _searchVideos


    fun fetchPopularVideos() {
        viewModelScope.launch {
            runCatching {
                val response = repository.getVideoInfo(
                    apiKey = RetroClient.API_KEY,
                    order = "mostPopular",
                    maxResult = 10,
                    regionCode = "KR",
                    pageToken = null
                )
                val videoModels = response.items!!.map {
                    HomeVideoModel(
                        id = it.id,
                        imgThumbnail = it.snippet?.thumbnails?.high?.url,
                        title = it.snippet?.title,
                        dateTime = it.snippet?.publishedAt,
                        description = it.snippet?.description,
                        Type = DataType.MOST.viewType
                    )
                }
                _videos.postValue(videoModels)
            }.onFailure { e ->
                Log.d("데이터 로딩 실패", e.toString())
            }
        }
    }

    fun fetchGameVideo() {
        viewModelScope.launch {
            runCatching {
                val response = repository.getVideoInfo(
                    apiKey = RetroClient.API_KEY,
                    maxResult = 10,
                    categoryId = "20",
                    regionCode = "KR",
                    pageToken = null
                )
                val videoModels = response.items!!.map {
                    HomeVideoModel(
                        id = it.id,
                        imgThumbnail = it.snippet?.thumbnails?.high?.url,
                        title = it.snippet?.title,
                        dateTime = it.snippet?.publishedAt,
                        description = it.snippet?.description,
                        Type = DataType.GAME.viewType
                    )
                }
                _gameVideos.postValue(videoModels)
            }.onFailure { e ->
                Log.d("게임 데이터 로딩 실패", e.toString())
            }
        }
    }

    fun fetchMusicVideo() {
        viewModelScope.launch {
            runCatching {
                val response = repository.getVideoInfo(
                    apiKey = RetroClient.API_KEY,
                    maxResult = 10,
                    categoryId = "10",
                    regionCode = "KR",
                    pageToken = null
                )
                val videoModels = response.items!!.map {
                    HomeVideoModel(
                        id = it.id,
                        imgThumbnail = it.snippet?.thumbnails?.high?.url,
                        title = it.snippet?.title,
                        dateTime = it.snippet?.publishedAt,
                        description = it.snippet?.description,
                        Type = DataType.MUSIC.viewType
                    )
                }
                _musicVideos.postValue(videoModels)
            }.onFailure { e ->
                Log.d("음악 데이터 로딩 실패", e.toString())
            }
        }
    }

    fun fetchPetVideo() {
        viewModelScope.launch {
            runCatching {
                val response = repository.getVideoInfo(
                    apiKey = RetroClient.API_KEY,
                    maxResult = 10,
                    categoryId = "15",
                    regionCode = "KR",
                    pageToken = null
                )
                val videoModels = response.items!!.map {
                    HomeVideoModel(
                        id = it.id,
                        imgThumbnail = it.snippet?.thumbnails?.high?.url,
                        title = it.snippet?.title,
                        dateTime = it.snippet?.publishedAt,
                        description = it.snippet?.description,
                        Type = DataType.MOVIE.viewType
                    )
                }
                _petVideos.postValue(videoModels)
            }.onFailure { e ->
                Log.d("펫 데이터 로딩 실패", e.toString())
            }
        }
    }

    fun searchVideos(searchQuery: String) {
        viewModelScope.launch {
            runCatching {
                val response = repository.searchVideo(
                    apiKey = RetroClient.API_KEY,
                    part = "snippet",
                    type = "video",
                    maxResult = 8, // 임시로 최대 8개 설정
                    regionCode = "KR",
                    q = searchQuery
                )
                val videoModels = response.items?.map {
                    SearchVideoModel(
                        id = it.id?.videoId ?: "",
                        imgThumbnail = it.snippet?.thumbnails?.high?.url,
                        title = it.snippet?.title,
                        dateTime = it.snippet?.publishedAt,
                        description = it.snippet?.description,
                        type = DataType.MOVIE.viewType
                    )
                } ?: emptyList()
                _searchVideos.postValue(videoModels)
            }.onFailure { e ->
                when (e) {
                    is HttpException -> {
                        val exception = null
                        when (exception?.code) {
                            403 -> Log.d("검색 실패", "API 호출 제한 초과: ${e.message}")
                            404 -> Log.d("검색 실패", "리소스를 찾을 수 없음: ${e.message}")
                            else -> Log.d("검색 실패", "HTTP 오류 발생: ${e.message}")
                        }
                    }

                    is IOException -> {
                        Log.d("검색 실패", "네트워크 오류: ${e.message}")
                    }

                    else -> {
                        Log.d("검색 실패", "기타 오류: ${e.message}")
                    }
                }
            }
        }
    }

    class HomeViewModelFactory : ViewModelProvider.Factory {

        private val repository = VideoApiServiceImpl(videoApiService = RetroClient.youtubeNetwork)

        override fun <T : ViewModel> create(
            modelClass: Class<T>,
            extras: CreationExtras
        ): T = HomeViewModel(
            repository
        ) as T
    }
}