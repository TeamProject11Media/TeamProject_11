package com.example.teamproject_11.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject_11.MainActivity
import com.example.teamproject_11.databinding.FragmentHomeBinding
import com.example.teamproject_11.home.adapter.GameViewAdapter
import com.example.teamproject_11.home.adapter.MostViewAdapter
import com.example.teamproject_11.home.adapter.MovieViewAdapter
import com.example.teamproject_11.home.adapter.MusicViewAdapter
import com.example.teamproject_11.home.data.HomeVideoModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var mostViewAdapter: MostViewAdapter

    private lateinit var gameViewAdapter: GameViewAdapter

    private lateinit var musicViewAdapter: MusicViewAdapter

    private lateinit var movieViewAdapter: MovieViewAdapter

    private val viewModel: HomeViewModel by viewModels()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
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

        initPopularVideo()
        initViewModel()
//        initGameVideo()
//        initMusicVideo()
//        initMovieVideo()


    }

    private fun initViewModel() {
        viewModel.video.observe(viewLifecycleOwner) {
            mostViewAdapter.itemList = it
            with(binding.rvMost) {
                adapter = mostViewAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }

//        viewModel.gameVideo.observe(viewLifecycleOwner) {
//            gameViewAdapter.itemList = it
//            with(binding.rvCategory2) {
//                adapter = gameViewAdapter
//                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            }
//        }

//        viewModel.musicVideo.observe(viewLifecycleOwner) {
//            musicViewAdapter.itemList = it
//            with(binding.rvCategory3) {
//                adapter = musicViewAdapter
//                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            }
//        }
//
//        viewModel.movieVideo.observe(viewLifecycleOwner) {
//            movieViewAdapter.itemList = it
//            with(binding.rvCategory4) {
//                adapter = movieViewAdapter
//                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            }
//        }
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

//    private fun initGameVideo() {
//        gameViewAdapter.setOnItemClickListener(object : GameViewAdapter.OnItemClickListener {
//            override fun onItemClick(videoModel: HomeVideoModel) {
//                (requireActivity() as MainActivity).openVideoDetailFromHome(videoModel)
//            }
//        })
//        CoroutineScope(Dispatchers.Main).launch {
//            fetchGameVideos()
//        }
//        viewModel.gameVideo.observe(viewLifecycleOwner) { videoModels ->
//            gameViewAdapter.setItem(videoModels)
//        }
//        fetchGameVideos()
//    }
//    private fun fetchGameVideos() {
//        viewModel.fetchGameVideo()
//    }
//
//    private fun initMusicVideo() {
//        musicViewAdapter.setOnItemClickListener(object : MusicViewAdapter.OnItemClickListener {
//            override fun onItemClick(videoModel: HomeVideoModel) {
//                (requireActivity() as MainActivity).openVideoDetailFromHome(videoModel)
//            }
//        })
//        CoroutineScope(Dispatchers.Main).launch {
//            fetchMusicVideos()
//        }
//        viewModel.musicVideo.observe(viewLifecycleOwner) { videoModels ->
//            musicViewAdapter.setItem(videoModels)
//        }
//        fetchMusicVideos()
//    }
//    private fun fetchMusicVideos() {
//        viewModel.fetchMusicVideo()
//    }
//
//    private fun initMovieVideo() {
//        movieViewAdapter.setOnItemClickListener(object : MovieViewAdapter.OnItemClickListener {
//            override fun onItemClick(videoModel: HomeVideoModel) {
//                (requireActivity() as MainActivity).openVideoDetailFromHome(videoModel)
//            }
//        })
//        CoroutineScope(Dispatchers.Main).launch {
//            fetchMovieVideos()
//        }
//        viewModel.movieVideo.observe(viewLifecycleOwner) { videoModels ->
//            movieViewAdapter.setItem(videoModels)
//        }
//        fetchMovieVideos()
//    }
//    private fun fetchMovieVideos() {
//        viewModel.fetchMovieVideo()
//    }
}
