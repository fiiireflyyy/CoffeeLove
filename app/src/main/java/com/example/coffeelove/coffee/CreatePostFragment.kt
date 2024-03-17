package com.example.coffeelove.coffee

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.coffeelove.R
import com.example.coffeelove.databinding.FragmentCreatePostBinding

class CreatePostFragment : Fragment() {



    private var _binding:FragmentCreatePostBinding?=null

    private val mBinding get()=_binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentCreatePostBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coffeeViewModel=ViewModelProvider(requireActivity())[CoffeeViewModel::class.java]




        mBinding.buttonCreatePost.setOnClickListener {
            val recipeName= mBinding.nameField.editText?.text.toString()

            val recipeDescription=mBinding.descriptionField.editText?.text.toString()
            val id=coffeeViewModel.getId()
            coffeeViewModel.createPost(id,recipeName,recipeDescription)
            Toast.makeText(context,"Создана",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_createPostFragment_to_myAccountFragment)
        }

        mBinding.buttonGoBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}