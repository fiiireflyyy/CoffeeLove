package com.example.coffeelove.map

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.coffeelove.R
import com.example.coffeelove.coffee.CoffeeViewModel
import com.example.coffeelove.databinding.FragmentMapBinding

class MapFragment : Fragment() {

    private var _binding : FragmentMapBinding?=null
    private val viewModel: CoffeeViewModel by activityViewModels<CoffeeViewModel>()
    private val mBinding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentMapBinding.inflate(inflater,container, false)
        //Перенести вызов в сплеш
        viewModel.addBackGroundUppLoad()
        viewModel.downLoadPersonal()


        return mBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}