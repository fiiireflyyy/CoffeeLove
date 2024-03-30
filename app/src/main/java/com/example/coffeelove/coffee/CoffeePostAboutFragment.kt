package com.example.coffeelove.coffee

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.coffeelove.R
import com.example.coffeelove.databinding.FragmentCoffeePostAboutBinding

class CoffeePostAboutFragment : Fragment() {

    private var _binding: FragmentCoffeePostAboutBinding?=null

    private val mBinding get()=_binding!!
    private val viewModel: CoffeeViewModel by activityViewModels<CoffeeViewModel>()
    lateinit var coffeePost:CoffeePost





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentCoffeePostAboutBinding.inflate(inflater,container,false)
        coffeePost = viewModel.getMoreCoffeePostAboutFragment()!!
        mBinding.userNameText.text=coffeePost.userNickname
        mBinding.coffeeRecipeName.text=coffeePost.recipeName
        mBinding.recipeDescription.text=coffeePost.recipeDescription
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.buttonGoBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        mBinding.userInfo.setOnClickListener {
            if(it!=mBinding.btnAdd){
                viewModel.downLoadOpenUser(coffeePost.userNickname!!)
                it.findNavController().navigate(R.id.action_coffeePostAboutFragment_to_accountFragment)
            }
        }

        mBinding.btnLike.setOnClickListener {
            val isContain=viewModel.getFavoritePostID().contains(coffeePost.id)
            if(isContain){
                Toast.makeText(requireContext(),"Пост уже добавлен в избранное",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.addFavoritePost(coffeePost.id!!)
                viewModel.getLiveDataFavorite().value?.add(coffeePost)
            }
        }

        mBinding.btnAdd.setOnClickListener {
            val isContain= viewModel.getMySubsLiveData().value?.contains(coffeePost.userNickname)
            if(isContain==true){
                Toast.makeText(requireContext(),"Вы уже подписаны",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.addSubscribers(coffeePost.userNickname!!)
                viewModel.getMySubsLiveData().value?.add(coffeePost.userNickname!!)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }



}