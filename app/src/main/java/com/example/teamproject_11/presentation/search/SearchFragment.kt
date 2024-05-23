package com.example.teamproject_11.presentation.search

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresExtension
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject_11.databinding.FragmentSearchBinding
import com.example.teamproject_11.presentation.home.main.HomeViewModel
import com.example.teamproject_11.presentation.home.model.HomeVideoModel
import com.example.teamproject_11.presentation.main.MainActivity

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

        searchAdapter = SearchAdapter(object : SearchAdapter.OnItemClickListener {
            override fun onClick(data: HomeVideoModel) {
                (requireActivity() as MainActivity).openVideoDetailFromHome(data)
            }
        })

        setRecyclerView()
        setUpListener()
        updateToScroll()
        observeViewModel()
    }

    private fun setRecyclerView() {
        binding.recyclerViewSearch.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
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

    private fun updateToScroll() {
        binding.recyclerViewSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.searchRecyclerContainer.canScrollVertically(1)) {
                    val listSize = viewModel.searchVideo.value!!.size - 1
                    viewModel.extraSearchVideos(binding.etSearch.text.toString())
                    (binding.recyclerViewSearch.adapter as SearchAdapter).notifyItemRangeChanged(
                        listSize,
                        8
                    )
                }
            }
        })
    }

    private fun observeViewModel() {
        viewModel.searchVideo.observe(viewLifecycleOwner) { searchVideo ->
            if (searchVideo != null) {
                searchAdapter.setItem(searchVideo)
                (binding.recyclerViewSearch.adapter as SearchAdapter).notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}