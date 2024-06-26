package com.example.coffeelove.view.fragment.coffee

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.coffeelove.R
import com.example.coffeelove.databinding.FragmentCreatePostBinding
import com.example.coffeelove.viewModel.CoffeeViewModel

class CreatePostFragment : Fragment() {


    private val viewModel: CoffeeViewModel by activityViewModels<CoffeeViewModel>()
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




        mBinding.imageButtonAdd.setOnClickListener {
            getContentFromGallery.launch("image/*")
        }
    }

    private val getContentFromGallery = registerForActivityResult(ActivityResultContracts.GetContent()){
        if (it != null){
            mBinding.placePic.setImageURI(it)
            viewModel.setLastUriImage(it)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}