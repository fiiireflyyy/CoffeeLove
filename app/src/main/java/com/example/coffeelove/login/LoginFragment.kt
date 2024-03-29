package com.example.coffeelove.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.coffeelove.R
import com.example.coffeelove.coffee.CoffeeViewModel
import com.example.coffeelove.databinding.FragmentLoginBinding
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage

class LoginFragment : Fragment() {


    private val viewModel: CoffeeViewModel by activityViewModels<CoffeeViewModel>()

    private var _binding:FragmentLoginBinding?=null
    val mBinding get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentLoginBinding.inflate(inflater,container,false)

        val message =RemoteMessage.Builder("Posts")
            .setData(mapOf("score" to "845", "time" to "22:15")).build()
        FirebaseMessaging.getInstance().send(message)

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