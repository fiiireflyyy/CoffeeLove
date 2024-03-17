package com.example.coffeelove.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.coffeelove.R
import com.example.coffeelove.coffee.CoffeeViewModel
import com.example.coffeelove.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {


    private val viewModel: CoffeeViewModel by activityViewModels<CoffeeViewModel>()

    private var _binding:FragmentLoginBinding?=null
    val mBinding get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentLoginBinding.inflate(inflater,container,false)
        //подкачка постов
        viewModel.getCountMyPost()

        mBinding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
        return mBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null

    }
}