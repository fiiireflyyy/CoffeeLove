package com.example.coffeelove.account

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coffeelove.databinding.FragmentAccountBinding
import com.example.coffeelove.databinding.FragmentMyAccountBinding

class MyAccountFragment : Fragment() {

    private var _binding: FragmentMyAccountBinding?=null

    private val binding get()=_binding!!

    private lateinit var viewModel: MyAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel: MyAccountViewModel =ViewModelProvider(this)[MyAccountViewModel::class.java]

        _binding=FragmentMyAccountBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}