package com.example.teamproject_11.presentation.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.teamproject_11.room.MyListDataBase
import com.example.teamproject_11.databinding.ActivityDetailBinding
import com.example.teamproject_11.presentation.home.model.HomeVideoModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailActivity : AppCompatActivity() {
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initRoom()
        settingDesc()
        settingImage()
        settingDate()
        initViewModel()
        updateToScroll()
        shareButton()
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
                    Log.d("데이터 추가 완료", "success")
                    Snackbar.make(binding.btnAddList, "내 리스트에 추가되었습니다.", Snackbar.LENGTH_SHORT)
                        .show()
                }
                    .onFailure { e ->
                        Snackbar.make(
                            binding.btnAddList,
                            "이미 리스트에 있는 동영상입니다.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
            }
        }
    }

    private fun settingImage() {
        viewModel.data.observe(this) {
            binding.detailPoster.load(it.imgThumbnail)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun settingDate() {
        viewModel.data.observe(this) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val date = LocalDateTime.parse(it.dateTime, formatter)
            binding.detailDate.text =
                date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        }
    }

    private fun settingDesc() {
        viewModel.data.observe(this) {
            binding.detailSummary.text = it.description
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

    private fun shareButton() {
        viewModel.data.observe(this) {
            val title = it.title
            val image = it.imgThumbnail
            val description = it.description
            val time = it.dateTime
            binding.btnShare.setOnClickListener {
                val share = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT, "동영상을 공유합니다!" +
                                "\n\n${title}" +
                                "\n\n${image}" +
                                "\n\n${description}" +
                                "\n\n${time}"
                    )
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(share, null))
            }
        }
    }
}