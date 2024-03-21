package com.example.coffeelove.account

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeelove.R
import com.example.coffeelove.coffee.CoffeeViewModel
import com.example.coffeelove.coffee.FavoritePostAdapter
import com.example.coffeelove.coffee.MyPostAdapter
import com.example.coffeelove.coffee.RecyclerAdapter
import com.example.coffeelove.databinding.FragmentAccountBinding
import com.example.coffeelove.databinding.FragmentMyAccountBinding
import com.google.android.material.tabs.TabLayout

class MyAccountFragment : Fragment() {

    private var _binding: FragmentMyAccountBinding?=null
    private val viewModel:CoffeeViewModel by activityViewModels<CoffeeViewModel>()
    private lateinit var tabLayout:TabLayout
    private lateinit var myPostAdapter: MyPostAdapter
    private lateinit var favoritePostAdapter: FavoritePostAdapter
    private val mBinding get()=_binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentMyAccountBinding.inflate(inflater, container, false)
        tabLayout=mBinding.profileTabs
//        viewModel.getMyPostFromBase()
//        viewModel.downLoadFavorite()
        mBinding.recyclerMyAccount.layoutManager = LinearLayoutManager(context)
        myPostAdapter= MyPostAdapter()
        favoritePostAdapter= FavoritePostAdapter()
        viewModel.getMyPostLiveData().observe(
            viewLifecycleOwner,
        ){
            array->myPostAdapter.myPostList=array
        }
        viewModel.getLiveDataFavorite().observe(
            viewLifecycleOwner,
        ){
            array2->favoritePostAdapter.myFavoriteList=array2
        }
        mBinding.recyclerMyAccount.adapter=myPostAdapter



        tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position=tab?.position
                when(position){
                    0->{
                        mBinding.recyclerMyAccount.adapter=myPostAdapter
                    }

                    1->{
                        mBinding.recyclerMyAccount.adapter=favoritePostAdapter
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })



        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        myRecyclerAdapter.myPostList=viewModel.getMyPost()
        mBinding.buttonCreatePost.setOnClickListener {
            findNavController().navigate(R.id.action_myAccountFragment_to_createPostFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
//        viewModel.getLiveDataFavorite().removeObservers(viewLifecycleOwner)
//        viewModel.getMyPostLiveData().removeObservers(viewLifecycleOwner)
    }

}