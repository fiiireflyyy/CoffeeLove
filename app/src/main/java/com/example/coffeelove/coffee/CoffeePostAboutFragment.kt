package com.example.coffeelove.coffee

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.coffeelove.R
import com.example.coffeelove.databinding.FragmentCoffeePostAboutBinding

class CoffeePostAboutFragment : Fragment() {

    private var _binding: FragmentCoffeePostAboutBinding?=null

    private val mBinding get()=_binding!!
    private val viewModel: CoffeeViewModel by activityViewModels<CoffeeViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val coffeePost :CoffeePost = viewModel.getMoreCoffeePostAboutFragment()!!
        _binding= FragmentCoffeePostAboutBinding.inflate(inflater,container,false)
        mBinding.userNameText.text=coffeePost.userNickname
        mBinding.coffeeRecipeName.text=coffeePost.recipeName
        mBinding.recipeDescription.text=coffeePost.recipeDescription
        return mBinding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }



}