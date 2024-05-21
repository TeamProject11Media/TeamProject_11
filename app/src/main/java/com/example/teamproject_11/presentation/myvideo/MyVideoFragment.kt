package com.example.teamproject_11.presentation.myvideo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.teamproject_11.databinding.FragmentMyVideoBinding
import com.example.teamproject_11.presentation.home.main.HomeViewModel
import com.example.teamproject_11.presentation.home.model.HomeVideoModel
import com.example.teamproject_11.presentation.main.MainActivity


class MyVideoFragment : Fragment() {
    private val binding by lazy { FragmentMyVideoBinding.inflate(layoutInflater) }
    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), HomeViewModel.HomeViewModelFactory())[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    //데이터가 갱신될 수 있도록 onResume()을 오버라이딩 해서 해당 리스트 갱신 메소드를 넣었습니다.
    override fun onResume() {
        super.onResume()
        viewModel.getMyVideoList(requireActivity())
    }

    private fun initView() {
        viewModel.getMyVideoList(requireActivity())
        viewModel.myVideoList.observe(viewLifecycleOwner){
            binding.myvideoRecyclerview.adapter = MyVideoAdapter(it, object : OnItemClick{
                override fun onItemClick(item: HomeVideoModel) {
                    (requireActivity() as MainActivity).openVideoDetailFromHome(item)
                }
            })
        }
        binding.myvideoRecyclerview.layoutManager = GridLayoutManager(this.context, 2)
    }
}
