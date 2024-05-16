package com.example.teamproject_11.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamproject_11.data.remote.model.YouTubeResponse
import com.example.teamproject_11.network.RetroClient
import com.example.teamproject_11.presentation.home.model.HomeVideoModel
import com.example.teamproject_11.presentation.main.DataType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//디테일 액티비티에서 쓸 뷰모델
class DetailViewModel: ViewModel() {

    private val apiService = RetroClient.youtubeNetwork

    private val _dummyData = MutableLiveData<List<HomeVideoModel>>()
    val dummyData : LiveData<List<HomeVideoModel>> get() = _dummyData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error



    private val _data : MutableLiveData<HomeVideoModel> = MutableLiveData()
    val data : LiveData<HomeVideoModel> get() = _data

    fun getClickData(data: HomeVideoModel){
        _data.postValue(data)
    }

    fun fetchPetVideo() {
        val call = apiService.getVideoInfo(
            order = "mostPopular",
            regionCode = "KR",
            maxResult = 20
        )
        call.enqueue(object : Callback<YouTubeResponse> {
            override fun onResponse(call: Call<YouTubeResponse>, response: Response<YouTubeResponse>) {
                if (response.isSuccessful) {
                    val videoItems = response.body()?.items ?: emptyList()
                    val videoModels = videoItems.map { videoItem ->
                        HomeVideoModel(
                            id = videoItem.id,
                            imgThumbnail = videoItem.snippet?.thumbnails?.default?.url,
                            title = videoItem.snippet?.title,
                            dateTime = videoItem.snippet?.publishedAt,
                            description = videoItem.snippet?.description,
                            Type = DataType.MOVIE.viewType
                        )
                    }
                    _dummyData.postValue(videoModels)
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