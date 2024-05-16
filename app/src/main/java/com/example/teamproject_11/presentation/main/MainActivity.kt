package com.example.teamproject_11.presentation.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.teamproject_11.R
import com.example.teamproject_11.databinding.ActivityMainBinding
import com.example.teamproject_11.presentation.home.model.HomeVideoModel
import com.example.teamproject_11.presentation.detail.DetailActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    var likedItems: ArrayList<HomeVideoModel> = ArrayList()

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val detailActivity = DetailActivity()
    private val bundle = Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        initViewPager()
        initToolBar()

    }

    private fun initViewPager() {


        val viewPager = binding.mainViewPager
        val tabLayout = binding.tablayout

        viewPager.run {
            isUserInputEnabled = false
        }

        viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Home"
                    tab.setIcon(R.drawable.home)
                }

                1 -> {
                    tab.text = "Search"
                    tab.setIcon(R.drawable.search)
                }

                2 -> {
                    tab.text = "My Video"
                    tab.setIcon(R.drawable.video)
                }
            }
        }.attach()

        binding.tablayout.tabTextColors =
            ContextCompat.getColorStateList(this@MainActivity, R.color.tab_select_color)
        binding.tablayout.tabIconTint =
            ContextCompat.getColorStateList(this@MainActivity, R.color.tab_select_color)
    }

    private fun initToolBar() {
        with(binding) {
            val viewPager = mainViewPager
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> {
                            tvTbTitle.text = "Home"
                        }

                        1 -> {
                            tvTbTitle.text = "Video List"
                        }

                        2 -> {
                            tvTbTitle.text = "My Videos"
                        }
                    }
                }
            })
            ivTbMain.setOnClickListener {
                var snackbar = Snackbar.make(constLayout, "공유 버튼 실행", Snackbar.LENGTH_SHORT)
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.setAction("닫기") {
                    snackbar.dismiss()
                }
                snackbar.show()
            }
        }
    }

    fun openVideoDetailFromHome(videoModel: HomeVideoModel) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("ClickItem", videoModel)
        startActivity(intent)
    }
}