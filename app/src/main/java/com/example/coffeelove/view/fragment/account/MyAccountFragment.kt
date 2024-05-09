package com.example.coffeelove.view.fragment.account

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
import com.example.coffeelove.viewModel.CoffeeViewModel
import com.example.coffeelove.view.adapters.FavoritePostAdapter
import com.example.coffeelove.view.adapters.MyPostAdapter
import com.example.coffeelove.view.adapters.MySubsAdapter
import com.example.coffeelove.databinding.FragmentMyAccountBinding
import com.google.android.material.tabs.TabLayout

class MyAccountFragment : Fragment() {

    private var _binding: FragmentMyAccountBinding?=null
    private val viewModel: CoffeeViewModel by activityViewModels<CoffeeViewModel>()
    private lateinit var tabLayout:TabLayout
    private lateinit var myPostAdapter: MyPostAdapter
    private lateinit var favoritePostAdapter: FavoritePostAdapter
    private lateinit var mySubsAdapter: MySubsAdapter
    private val mBinding get()=_binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentMyAccountBinding.inflate(inflater, container, false)
        mBinding.userNameProfile.text = viewModel.getCurrentUserName()
        tabLayout=mBinding.profileTabs
        viewModel.getMyPostFromBase()
        mBinding.recyclerMyAccount.layoutManager = LinearLayoutManager(context)
        myPostAdapter= MyPostAdapter(this, viewModel)
        favoritePostAdapter= FavoritePostAdapter(this,viewModel)
        mySubsAdapter= MySubsAdapter(this,viewModel)

        viewModel.getMyPostLiveData().observe(
            viewLifecycleOwner,
        ){ array->
            if (array.isNotEmpty()) {
                myPostAdapter.myPostList = array
            }
            myPostAdapter.notifying()
            Log.d("RRR", array.toString())
        }

        viewModel.getLiveDataFavorite().observe(
            viewLifecycleOwner,
        ){ array2->
            if (array2.isNotEmpty()) {
                favoritePostAdapter.myFavoriteList=array2
            }
            favoritePostAdapter.notifying()

        }
        viewModel.getMySubsLiveData().observe(
            viewLifecycleOwner,
        ){ array3->
            mySubsAdapter.mySubsList=array3
            mySubsAdapter.notifying()
        }

        mBinding.recyclerMyAccount.adapter=myPostAdapter




        tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position=tab?.position
                when(position){
                    0->{
                        viewModel.getMyPostFromBase()

                        //Log.d("RRR", "tab 1 " + myPostAdapter.myPostList.toString())
                        mBinding.recyclerMyAccount.adapter=myPostAdapter
                    }

                    1->{
                        viewModel.downLoadFavorite()
                        mBinding.recyclerMyAccount.adapter=favoritePostAdapter
                    }

                    2->{
                        viewModel.getMySubs()
                        mBinding.recyclerMyAccount.adapter=mySubsAdapter
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