package com.example.coffeelove.view.fragment.coffee

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeelove.databinding.FragmentCoffeeBinding
import com.example.coffeelove.view.adapters.RecyclerAdapter
import com.example.coffeelove.viewModel.CoffeeViewModel
import com.google.android.material.tabs.TabLayout

class CoffeeFragment : Fragment() {

    private var _binding:FragmentCoffeeBinding?=null

    private val mBinding get()=_binding!!
    private val viewModel: CoffeeViewModel by activityViewModels<CoffeeViewModel>()
    private lateinit var recyclerAllAdapter: RecyclerAdapter
    private lateinit var recomendPostAdapter: RecyclerAdapter
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentCoffeeBinding.inflate(inflater,container,false)

        tabLayout=mBinding.profileTabs

        recyclerAllAdapter= RecyclerAdapter(this, viewModel)
        recomendPostAdapter= RecyclerAdapter(this, viewModel)
        mBinding.recyclerCoffeePost.layoutManager=LinearLayoutManager(context)
        recyclerAllAdapter.notesList=viewModel.getListLiveData()!!
        mBinding.recyclerCoffeePost.adapter=recyclerAllAdapter

        //ПЕРЕПИСАТЬ ПОСЛЕ УЛУЧШЕНИЯ ДИФУТИЛЯ
        viewModel.getRecomendLive().observe(
            viewLifecycleOwner,
        ){ array->
            if(array.isNotEmpty()) {
                recomendPostAdapter.notesList = array
                recomendPostAdapter.notifying()
                Log.d("RRR",array.toString())
            }
        }



        tabLayout.addOnTabSelectedListener(object:
            TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position=tab?.position
                when(position){
                    0->{
                        mBinding.message.visibility=View.GONE
                        mBinding.recyclerCoffeePost.visibility=View.VISIBLE
                        mBinding.recyclerCoffeePost.adapter=recyclerAllAdapter
                    }
                    1->{//ПЕРЕПИСАТЬ ПОСЛЕ УЛУЧШЕНИЯ ДИФУТИЛЯ
                        if (viewModel.getCurrentUserName()!="гость"){
                            viewModel.downLoadPersonal()
                            mBinding.recyclerCoffeePost.adapter=recomendPostAdapter
                        }
                        else{
                            mBinding.recyclerCoffeePost.visibility=View.GONE
                            mBinding.message.visibility=View.VISIBLE
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })


        //ЗАПРОС ДАННЫХ ДАННЫХ ИЗ РЕПОЗИТОРИЯ НА МОМЕНТ СОЗДАНИЯ ФРАГМЕНТА


//        СЛУШАТЕЛЬ ДАННЫХ И ЛОГИКА ДЕЙСТВИЙ ПРИ УВЕДОМЛЕНИИ ОБ ИЗМЕНЕНИИ
//        viewModel.getListTest().observe(
//            viewLifecycleOwner){
//            array->recyclerAdapter.notesList=array
//            recyclerAdapter.notifying()
//            Log.d("paramparam", array.toString())
//        }


        return mBinding.root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}