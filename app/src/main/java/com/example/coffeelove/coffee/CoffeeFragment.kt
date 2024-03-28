package com.example.coffeelove.coffee

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeelove.databinding.FragmentCoffeeBinding

class CoffeeFragment : Fragment() {

    private var _binding:FragmentCoffeeBinding?=null

    private val mBinding get()=_binding!!
    private val viewModel: CoffeeViewModel by activityViewModels<CoffeeViewModel>()
    private lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentCoffeeBinding.inflate(inflater,container,false)


        recyclerAdapter= RecyclerAdapter(this, viewModel)
        mBinding.recyclerCoffeePost.layoutManager=LinearLayoutManager(context)
        mBinding.recyclerCoffeePost.adapter=recyclerAdapter
        //ЗАПРОС ДАННЫХ ДАННЫХ ИЗ РЕПОЗИТОРИЯ НА МОМЕНТ СОЗДАНИЯ ФРАГМЕНТА
        recyclerAdapter.notesList=viewModel.getListLiveData()!!

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