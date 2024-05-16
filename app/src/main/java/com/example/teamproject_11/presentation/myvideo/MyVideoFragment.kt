package com.example.teamproject_11.presentation.myvideo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.teamproject_11.R
import com.example.teamproject_11.data.room.MyListDataBase
import com.example.teamproject_11.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [MyVideoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class MyVideoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var param1: String? = null
    private var param2: String? = null

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


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_video, container, false)
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