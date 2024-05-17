package com.example.teamproject_11.presentation.detail

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.teamproject_11.room.MyListDataBase
import com.example.teamproject_11.databinding.ActivityDetailBinding
import com.example.teamproject_11.presentation.home.model.HomeVideoModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
//        initRoom()
        settingDesc()
        settingImage()
        settingDate()

        initViewModel()
    }



    private fun initView(){
        val toolBar = binding.detailToolBar
        setSupportActionBar(toolBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel.getClickData(data!!)
    }
    private fun initRoom(){
        //Room 관련 테스트용... 저장되는거 확인, 오류 캐치하는 기능 만들기
        val listDao = MyListDataBase.getMyListDataBase(this).getMyListDAO()
        binding.btnAddList.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                runCatching {
                    listDao.insertData(data!!)
                }.onSuccess {
                    Toast.makeText(this@DetailActivity, "내 리스트에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                }
                    .onFailure {
                }
                }

            }
    }
    private fun settingImage(){
        viewModel.data.observe(this){
            binding.detailPoster.load(it.imgThumbnail)
        }
    }
    private fun settingDate(){
        viewModel.data.observe(this){
            binding.detailDate.text = it.dateTime
        }
    }
    private fun settingDesc(){
        viewModel.data.observe(this){
            binding.detailSummary.text = it.description
        }
    }
    //뒤로 가기 버튼 누르기
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViewModel() {
        viewModel.dummyData.observe(this) {
            detailAdapter = DetailAdapter()
            detailAdapter.itemList = it
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
}