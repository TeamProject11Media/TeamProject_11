package com.example.teamproject_11.presentation.home.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject_11.R
import com.example.teamproject_11.databinding.FragmentHomeBinding
import com.example.teamproject_11.presentation.home.main.adapter.GameViewAdapter
import com.example.teamproject_11.presentation.home.main.adapter.MostViewAdapter
import com.example.teamproject_11.presentation.home.main.adapter.MovieViewAdapter
import com.example.teamproject_11.presentation.home.main.adapter.MusicViewAdapter
import com.example.teamproject_11.presentation.home.main.adapter.SelectViewAdapter
import com.example.teamproject_11.presentation.home.model.HomeVideoModel
import com.example.teamproject_11.presentation.main.MainActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mostViewAdapter: MostViewAdapter
    private lateinit var gameViewAdapter: GameViewAdapter
    private lateinit var musicViewAdapter: MusicViewAdapter
    private lateinit var movieViewAdapter: MovieViewAdapter
    private lateinit var selectViewAdapter: SelectViewAdapter

    private val viewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            HomeViewModel.HomeViewModelFactory()
        )[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mostViewAdapter = MostViewAdapter()
        gameViewAdapter = GameViewAdapter()
        musicViewAdapter = MusicViewAdapter()
        movieViewAdapter = MovieViewAdapter()
        selectViewAdapter = SelectViewAdapter()

        initPopularVideo()
        initViewModel()
        initGameVideo()
        initMusicVideo()
        initPetVideo()
        initSelectVideo()
    }

    private fun initViewModel() {
        viewModel.video.observe(viewLifecycleOwner) {
            mostViewAdapter.itemList = it
            with(binding.rvMost) {
                adapter = mostViewAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }

        viewModel.gameVideo.observe(viewLifecycleOwner) {
            gameViewAdapter.itemList = it
            with(binding.rvCategory2) {
                adapter = gameViewAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }

        viewModel.musicVideo.observe(viewLifecycleOwner) {
            musicViewAdapter.itemList = it
            with(binding.rvCategory3) {
                adapter = musicViewAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }

        viewModel.petVideo.observe(viewLifecycleOwner) {
            movieViewAdapter.itemList = it
            with(binding.rvCategory4) {
                adapter = movieViewAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }

        viewModel.selectVideo.observe(viewLifecycleOwner) {
            selectViewAdapter.itemList = it
            with(binding.rvCategory1) {
                adapter = selectViewAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    private fun initPopularVideo() {
        mostViewAdapter.setOnItemClickListener(object : MostViewAdapter.OnItemClickListener {
            override fun onItemClick(videoModel: HomeVideoModel) {
                (requireActivity() as MainActivity).openVideoDetailFromHome(videoModel)
            }
        })
        viewModel.video.observe(viewLifecycleOwner) { videoModels ->
            mostViewAdapter.setItems(videoModels)
        }
        fetchPopularVideos()
    }

    private fun fetchPopularVideos() {
        viewModel.fetchPopularVideos()
    }

    private fun initGameVideo() {
        gameViewAdapter.setOnItemClickListener(object : GameViewAdapter.OnItemClickListener {
            override fun onItemClick(videoModel: HomeVideoModel) {
                (requireActivity() as MainActivity).openVideoDetailFromHome(videoModel)
            }
        })
        viewModel.gameVideo.observe(viewLifecycleOwner) { videoModels ->
            gameViewAdapter.setItem(videoModels)
        }
        fetchGameVideos()
    }

    private fun fetchGameVideos() {
        viewModel.fetchGameVideo()
    }

    private fun initMusicVideo() {
        musicViewAdapter.setOnItemClickListener(object : MusicViewAdapter.OnItemClickListener {
            override fun onItemClick(videoModel: HomeVideoModel) {
                (requireActivity() as MainActivity).openVideoDetailFromHome(videoModel)
            }
        })
        viewModel.musicVideo.observe(viewLifecycleOwner) { videoModels ->
            musicViewAdapter.setItem(videoModels)
        }
        fetchMusicVideos()
    }

    private fun fetchMusicVideos() {
        viewModel.fetchMusicVideo()
    }

    private fun initPetVideo() {
        movieViewAdapter.setOnItemClickListener(object : MovieViewAdapter.OnItemClickListener {
            override fun onItemClick(videoModel: HomeVideoModel) {
                (requireActivity() as MainActivity).openVideoDetailFromHome(videoModel)
            }
        })
        viewModel.petVideo.observe(viewLifecycleOwner) { videoModels ->
            movieViewAdapter.setItem(videoModels)
        }
        fetchPetVideos()
    }

    private fun fetchPetVideos() {
        viewModel.fetchPetVideo()
    }

    private fun initSelectVideo() {
        selectViewAdapter.setOnItemClickListener(object : SelectViewAdapter.OnItemClickListener {
            override fun onItemClick(videoModel: HomeVideoModel) {
                (requireActivity() as MainActivity).openVideoDetailFromHome(videoModel)
            }
        })
        viewModel.selectVideo.observe(viewLifecycleOwner) { videoModels ->
            selectViewAdapter.setItem(videoModels)
        }
        fetchSelectVideo()
    }

    private fun fetchSelectVideo() {
        with(binding.tvMainCategory1) {
            adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.spinner_list,
                R.layout.spinner_item
            )
            onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        when (position) {
                            0 -> viewModel.fetchSelectVideo("1")
                            1 -> viewModel.fetchSelectVideo("24")
                            2 -> viewModel.fetchSelectVideo("2")
                            3 -> viewModel.fetchSelectVideo("17")
                            4 -> viewModel.fetchSelectVideo("23")
                            5 -> viewModel.fetchSelectVideo("26")
                            6 -> viewModel.fetchSelectVideo("28")
                            7 -> viewModel.fetchSelectVideo("25")
                        }
                    }
                }
        }
    }
}