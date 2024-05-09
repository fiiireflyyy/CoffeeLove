package com.example.coffeelove.view.fragment.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.coffeelove.R
import com.example.coffeelove.viewModel.CoffeeViewModel
import com.example.coffeelove.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {


    private val viewModel: CoffeeViewModel by activityViewModels<CoffeeViewModel>()

    private var _binding:FragmentLoginBinding?=null
    private lateinit var auth: FirebaseAuth

    val mBinding get()=_binding!!


    override fun onStart() {
        super.onStart()
        val currentUser=null
        if(currentUser!=null){
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentLoginBinding.inflate(inflater,container,false)
        auth=Firebase.auth






        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        mBinding.btnLogin.setOnClickListener {
            val email = mBinding.loginField.editText?.text.toString()
            val password = mBinding.registerField.editText?.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("RRR", "Успешно вход")
                        val user = auth.currentUser
                        viewModel.setCurrentUser(user)
                        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                    } else {
                        Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        mBinding.btnReg.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

        }




        mBinding.singInAsGuest.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null

    }
}