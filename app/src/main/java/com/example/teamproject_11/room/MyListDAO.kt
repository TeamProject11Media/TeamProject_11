package com.example.teamproject_11.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.teamproject_11.presentation.home.model.HomeVideoModel

@Dao
interface MyListDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: HomeVideoModel)

    @Query("SELECT * FROM MyList")
    fun getAllListData(): LiveData<List<HomeVideoModel>>

    @Query("SELECT * FROM MYLIST")
    fun getMyListData(): List<HomeVideoModel>

    @Delete
    fun deleteData(data: HomeVideoModel)
}