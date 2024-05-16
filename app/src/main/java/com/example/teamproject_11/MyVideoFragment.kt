package com.example.teamproject_11

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.teamproject_11.databinding.ActivityMainBinding
import com.example.teamproject_11.databinding.FragmentMyVideoBinding
import com.example.teamproject_11.home.data.HomeVideoModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyVideoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyVideoFragment : Fragment() {

    private lateinit var mContext: Context

    // 바인딩 객체를 null 허용으로 설정 (프래그먼트의 뷰가 파괴될 때 null 처리하기 위함)
    private var binding: FragmentMyVideoBinding? = null
    private lateinit var adapter: MyVideoAdapter

    // 사용자의 좋아요를 받은 항목을 저장하는 리스트
    private var likedItems: List<HomeVideoModel> = listOf()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // MainActivity로부터 좋아요 받은 항목을 가져옴
        val mainActivity = activity as MainActivity
        likedItems = mainActivity.itemList

        Log.d("BookmarkFragment", "#jblee likedItems size = ${likedItems.size}")

        // 어댑터 설정
        adapter = MyVideoAdapter(mContext).apply {
            items = likedItems.toMutableList()
        }

        // 바인딩 및 RecyclerView 설정
        binding = FragmentBookmarkBinding.inflate(inflater, container, false).apply {
            rvBookmark.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rvBookmark.adapter = adapter
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyVideoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyVideoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }



    private fun initView() {
        // room에 저장되어있는 내 비디오 리스트 불러오기
        val listDao = MyListDataBase.getMyListDataBase(requireActivity()).getMyListDAO()
        CoroutineScope(Dispatchers.IO).launch {
            val list = listDao.getMyListData()
            Log.d("룸 데이터 확인", list.toString())
        }
    }
}