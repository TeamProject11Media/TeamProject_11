package com.example.teamproject_11.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.example.teamproject_11.presentation.home.model.HomeVideoModel

@Dao
interface MyListDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: HomeVideoModel) //key 충돌이 나면 새 데이터 교체

    @Query("SELECT * FROM MyList")
    fun getAllStudents(): LiveData<List<HomeVideoModel>>

//    @Query("SELECT * FROM MyList WHERE id = :id")   // 메소드 인자를 SQL문에서 :을 붙여 사용
//    suspend fun getStudentByName(id: String): List<HomeVideoModel>

    @Query("SELECT * FROM MYLIST")
    fun getMyListData() : List<HomeVideoModel>
}

