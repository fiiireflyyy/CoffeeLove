package com.example.coffeelove.account

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeelove.R
import com.example.coffeelove.coffee.CoffeeViewModel
import com.example.coffeelove.coffee.MyPostAdapter
import com.example.coffeelove.coffee.RecyclerAdapter
import com.example.coffeelove.databinding.FragmentAccountBinding
import com.example.coffeelove.databinding.FragmentMyAccountBinding

class MyAccountFragment : Fragment() {

    private var _binding: FragmentMyAccountBinding?=null
    private val viewModel:CoffeeViewModel by activityViewModels<CoffeeViewModel>()
    private lateinit var myRecyclerAdapter: MyPostAdapter
    private val mBinding get()=_binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentMyAccountBinding.inflate(inflater, container, false)
        myRecyclerAdapter= MyPostAdapter()
        mBinding.recyclerMyAccount.layoutManager=LinearLayoutManager(context)
        mBinding.recyclerMyAccount.adapter=myRecyclerAdapter
        myRecyclerAdapter.myPostList=viewModel.getMyPost()

        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myRecyclerAdapter.myPostList=viewModel.getMyPost()
        mBinding.buttonCreatePost.setOnClickListener {
            findNavController().navigate(R.id.action_myAccountFragment_to_createPostFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}