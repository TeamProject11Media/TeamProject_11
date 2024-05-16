package com.example.teamproject_11.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

import com.example.teamproject_11.presentation.home.model.HomeVideoModel

@Dao
interface MyListDAO {
    @Insert
    fun insertData(data: HomeVideoModel)
    @Query("SELECT * FROM MYLIST")
    fun getMyListData() : List<HomeVideoModel>
}

