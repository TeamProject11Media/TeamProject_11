package com.example.teamproject_11.presentation.main

// HomeVideoModel의 타입 구분을 위한 enum class
enum class DataType(val viewType: Int) {
    MOST(viewType = 0),
    MOVIE(viewType = 1),
    GAME(viewType = 2),
    MUSIC(viewType = 3),
    SEARCH_RESULT(viewType = 4)
}