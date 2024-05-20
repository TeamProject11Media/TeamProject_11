package com.example.teamproject_11.presentation.myvideo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.protobuf.LazyStringArrayList
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.teamproject_11.room.MyListDataBase
import com.example.teamproject_11.databinding.FragmentMyVideoBinding
import com.example.teamproject_11.presentation.detail.DetailActivity
import com.example.teamproject_11.presentation.detail.DetailViewModel
import com.example.teamproject_11.presentation.detail.DetailViewModelFactory
import com.example.teamproject_11.presentation.home.model.HomeVideoModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Array

/**
 * A simple [Fragment] subclass.
 * Use the [MyVideoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM = "param"

class MyVideoFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var binding: FragmentMyVideoBinding? = null
    private var myVideos: List<HomeVideoModel> = listOf()
    private lateinit var adapter: MyVideoAdapter
    private lateinit var mContext: Context


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val detailActivity = DetailActivity()
//        myVideos = viewModel.likedItems
//
//        Log.d("MyVideoFragment myVideos 확인", "$myVideos")
//        Log.d("MyVideoFragment likedItems 확인", "${viewModel.likedItems}")

        adapter = MyVideoAdapter(mContext).apply {
            items = myVideos.toMutableList()
        }

        binding = FragmentMyVideoBinding.inflate(inflater, container, false).apply {
            myVideoRecycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            myVideoRecycler.adapter = adapter

        }

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }



    private fun initView() {
        // room에 저장되어있는 내 비디오 리스트 불러오기
        val listDao = MyListDataBase.getMyListDataBase(requireActivity()).getMyListDAO()
        CoroutineScope(Dispatchers.IO).launch {
            myVideos = listDao.getMyListData()
            Log.d("룸 데이터 확인", myVideos.toString())
        }
    }
}