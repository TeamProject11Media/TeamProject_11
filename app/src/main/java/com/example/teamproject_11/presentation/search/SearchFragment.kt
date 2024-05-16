package com.example.teamproject_11.presentation.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject_11.databinding.FragmentSearchBinding
import com.example.teamproject_11.presentation.detail.DetailActivity
import com.google.gson.Gson

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SearchAdapter 초기화
        searchAdapter = SearchAdapter(emptyList(), object : SearchAdapter.OnItemClickListener {
            // 아이템 클릭시 DetailActivity intent 전달
            override fun onClick(view: View, position: Int) {
                val selectedItem = searchAdapter.items[position]
                val intent = Intent(context, DetailActivity::class.java)
                val json = Gson().toJson(selectedItem.items)
                intent.putExtra("searchQuery", json)
                startActivity(intent)
            }
        })

        setRecyclerView()
        setUpListener()
    }

    private fun setRecyclerView() {
        binding.recyclerViewSearch.adapter = searchAdapter
        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(context)
    }

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
    }
}