package com.example.coffeelove.login

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
import com.example.coffeelove.coffee.CoffeeViewModel
import com.example.coffeelove.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {

    private var _binding:FragmentRegisterBinding?=null
    private val viewModel: CoffeeViewModel by activityViewModels<CoffeeViewModel>()


    private lateinit var auth: FirebaseAuth
    val mBinding get()=_binding!!



    override fun onStart() {
        super.onStart()
        val currentUser=auth
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding=FragmentRegisterBinding.inflate(inflater,container,false)
        auth= Firebase.auth
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        mBinding.btnReg.setOnClickListener {
            val email= mBinding.loginField.editText?.text.toString()
            Log.d("RRR",email)
            val password= mBinding.registerField.editText?.text.toString()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{task->
                    if (task.isSuccessful){
                        Toast.makeText(requireContext(),"Пользователь зарегистрирован",Toast.LENGTH_SHORT).show()
                        val user=auth.currentUser
                        viewModel.createUser(user)
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                    else{
                        Toast.makeText(requireContext(),"Ошибка",Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}