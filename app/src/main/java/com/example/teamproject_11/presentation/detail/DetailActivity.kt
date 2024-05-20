package com.example.teamproject_11.presentation.detail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.teamproject_11.room.MyListDataBase
import com.example.teamproject_11.databinding.ActivityDetailBinding
import com.example.teamproject_11.presentation.home.model.HomeVideoModel
import com.example.teamproject_11.room.MyListDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    lateinit var myListDAO: MyListDAO
    val likedItems = mutableListOf<HomeVideoModel>()

    private val binding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy {
        ViewModelProvider(this, DetailViewModelFactory())[DetailViewModel::class.java]
    }
    private val data by lazy {
        intent.getParcelableExtra<HomeVideoModel>("ClickItem")
    }
    private lateinit var detailAdapter: DetailAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
//        initRoom()
        settingDesc()
        settingImage()
        settingDate()
        initViewModel()
        myListDAO = MyListDataBase.getMyListDataBase(this).getMyListDAO()

        //MyVideo 추가 버튼
        binding.btnAddList.setOnClickListener {
            myVideosAdd()
        }
        updateToScroll()
    }

    //더보기 기능을 onResume에 넣었습니다
    override fun onResume() {
        super.onResume()
        setViewMore(binding.detailSummary, binding.detailSummaryMore)
    }

    private fun initView() {
        val toolBar = binding.detailToolBar
        binding.detailToolBar.title = ""
        setSupportActionBar(toolBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel.getClickData(data!!)
    }

    private fun initRoom() {
        //Room 관련 테스트용... 저장되는거 확인, 오류 캐치하는 기능 만들기
        val listDao = MyListDataBase.getMyListDataBase(this).getMyListDAO()
        binding.btnAddList.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                runCatching {
                    listDao.insertData(data!!)
                }.onSuccess {
                    Toast.makeText(this@DetailActivity, "내 리스트에 추가되었습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
                    .onFailure {
                    }
            }

        }
    }

    private fun settingImage() {
        viewModel.data.observe(this) {
            binding.detailPoster.load(it.imgThumbnail)
        }
    }

    private fun settingDate() {
        viewModel.data.observe(this) {
            binding.detailDate.text = it.dateTime
        }
    }

    private fun settingDesc() {
        viewModel.data.observe(this) {
            binding.detailSummary.text = it.description
        }
    }

    private fun myVideosAdd() {
        viewModel.data.observe(this) {
            if (!likedItems.contains(it)) {
                likedItems.add(it)


                Log.d("뷰모델 리스트 확인", "${likedItems}")
                CoroutineScope(Dispatchers.IO).launch {
                    myListDAO.insertData(it)
                    Log.d("room데이터 확인", "${myListDAO}")
                }
            }
        }
    }

    //뒤로 가기 버튼 누르기
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViewModel() {
        viewModel.dummyData.observe(this) {
            detailAdapter = DetailAdapter()
            detailAdapter.itemList = it.toMutableList()
            with(binding.detailRecommandList) {
                adapter = detailAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
            detailAdapter.setItem(it)
        }
        fetchVideo()
    }

    private fun fetchVideo() {
        viewModel.fetchPetVideo()
    }


    //마이 비디오 리스트 추가
//    fun addLikedItem(item: HomeVideoModel) {
//        if (!likedItems.contains(item)) {
//            likedItems.add(item)
//            Log.d("아이템 확인", "$item")
//        }
//    }

//    fun removeLikedItem(item: HomeVideoModel) {
//        likedItems.remove(item)
//    }

    //요약에서 더보기 버튼 활성화 관련 메소드
    private fun setViewMore(contentTextView: TextView, viewMoreTextView: TextView) {
        contentTextView.post {
            val lineCount = contentTextView.layout.lineCount
            if (lineCount > 0) {
                if (contentTextView.layout.getEllipsisCount(lineCount - 1) > 0) {
                    viewMoreTextView.visibility = View.VISIBLE

                    viewMoreTextView.setOnClickListener {
                        contentTextView.maxLines = Int.MAX_VALUE
                        viewMoreTextView.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun updateToScroll() {
        binding.detailRecommandList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.detailContainer.canScrollVertically(1)) {
                    val listSize = viewModel.dummyData.value!!.size - 1
                    viewModel.addFetchVideo()
                    (binding.detailRecommandList.adapter as DetailAdapter).notifyItemRangeChanged(
                        listSize,
                        20
                    )
                }
            }
        })
    }

}