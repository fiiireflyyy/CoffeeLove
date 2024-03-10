package com.example.coffeelove.coffee

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coffeelove.databinding.FragmentCoffeeBinding

class CoffeeFragment : Fragment() {

    private var _binding:FragmentCoffeeBinding?=null

    private val binding get()=_binding!!

    private lateinit var viewModel:CoffeeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val homeViewModel:CoffeeViewModel=ViewModelProvider(this)[CoffeeViewModel::class.java]
        _binding=FragmentCoffeeBinding.inflate(inflater,container,false)

        return _binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}