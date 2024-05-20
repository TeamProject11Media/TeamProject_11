package com.example.teamproject_11.presentation.search

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresExtension
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject_11.databinding.FragmentSearchBinding
import com.example.teamproject_11.presentation.home.main.HomeViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!
    private lateinit var searchAdapter: SearchAdapter
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.HomeViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchAdapter = SearchAdapter(emptyList(), object : SearchAdapter.OnItemClickListener {
            override fun onClick(view: View, position: Int) {
                // 클릭한 비디오를 디테일 액티비티로 전달
//                val videoModel = searchAdapter.items[position]
//                (requireActivity() as MainActivity).openVideoDetailFromHome(videoModel)
            }
        })

        setRecyclerView()
        setUpListener()
        observeViewModel()
    }

    private fun setRecyclerView() {
        Log.d("SearchFragment", "setRecyclerView called")
        binding.recyclerViewSearch.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun setUpListener() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            hideKeyboard()
            // 검색어 입력하고 엔터 누르면 검색 실행
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun hideKeyboard() {
        val imm =
            view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun performSearch() {
        val searchQuery = binding.etSearch.text.toString()
        viewModel.searchVideos(searchQuery)
    }

    private fun observeViewModel() {
        viewModel.searchVideo.observe(viewLifecycleOwner) { searchVideo ->
            if (searchVideo != null) {
                searchAdapter.updateItems(data = searchVideo)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}