package com.example.teamproject_11.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.teamproject_11.data.repository.VideoApiServiceImpl
import com.example.teamproject_11.domain.repository.YouTubeRepository
import com.example.teamproject_11.network.RetroClient
import com.example.teamproject_11.presentation.home.model.HomeVideoModel
import com.example.teamproject_11.presentation.main.DataType
import kotlinx.coroutines.launch

//디테일 액티비티에서 쓸 뷰모델
class DetailViewModel(
    private val repository: YouTubeRepository
) : ViewModel() {

    private val _dummyData = MutableLiveData<List<HomeVideoModel>>()
    val dummyData: LiveData<List<HomeVideoModel>> get() = _dummyData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var nextPageToken: String? = null

    private val _data: MutableLiveData<HomeVideoModel> = MutableLiveData()
    val data: LiveData<HomeVideoModel> get() = _data

    fun getClickData(data: HomeVideoModel) {
        _data.postValue(data)
    }

    fun fetchPetVideo() {
        viewModelScope.launch {
            runCatching {
                val response = repository.getVideoInfo(
                    apiKey = RetroClient.API_KEY,
                    order = "mostPopular",
                    regionCode = "KR",
                    maxResult = 20,
                    pageToken = null,
                )
                nextPageToken = response.nextPageToken
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
                _dummyData.postValue(videoModels)
            }.onFailure {
                _error.postValue("Error fetching videos")
            }
        }
    }

    fun addFetchVideo() {
        viewModelScope.launch {
            runCatching {
                val buffer = _dummyData.value!!.toMutableList()
                val response = repository.getVideoInfo(
                    apiKey = RetroClient.API_KEY,
                    order = "mostPopular",
                    regionCode = "KR",
                    maxResult = 20,
                    pageToken = nextPageToken
                )
                nextPageToken = response.nextPageToken
                response.items?.forEach {
                    val extraData = HomeVideoModel(
                        id = it.id,
                        imgThumbnail = it.snippet?.thumbnails?.high?.url,
                        title = it.snippet?.title,
                        dateTime = it.snippet?.publishedAt,
                        description = it.snippet?.description,
                        Type = DataType.MOST.viewType
                    )
                    buffer.add(extraData)
                }
                _dummyData.postValue(buffer)
                Log.d("성공", "데이터 추가 성공")
            }.onFailure {
                Log.d("오류", "데이터 추가 실패")
            }
        }
    }
}

class DetailViewModelFactory : ViewModelProvider.Factory {

    private val repository = VideoApiServiceImpl(RetroClient.youtubeNetwork)

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T = DetailViewModel(
        repository
    ) as T
}