package com.example.coffeelove.view.fragment.coffee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.coffeelove.R
import com.example.coffeelove.databinding.FragmentCoffeePostAboutBinding
import com.example.coffeelove.viewModel.CoffeeViewModel
import com.google.firebase.database.core.Context

class CoffeePostAboutFragment : Fragment() {

    private var _binding: FragmentCoffeePostAboutBinding?=null

    private val mBinding get()=_binding!!
    private val viewModel: CoffeeViewModel by activityViewModels<CoffeeViewModel>()
    lateinit var coffeePost: CoffeePost





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

//        mBinding.btnLike.setOnClickListener {
//            val isContain=viewModel.getFavoritePostID().contains(coffeePost.id)
//            if(isContain){
//                Toast.makeText(requireContext(),"Пост уже добавлен в избранное",Toast.LENGTH_SHORT).show()
//            }else{
//                viewModel.addFavoritePost(coffeePost.id!!)
//                viewModel.getLiveDataFavorite().value?.add(coffeePost)
//            }
//        }

//        mBinding.btnAdd.setOnClickListener {
//            val isContain= viewModel.getMySubsLiveData().value?.contains(coffeePost.userNickname)
//            if(isContain==true){
//                Toast.makeText(requireContext(),"Вы уже подписаны",Toast.LENGTH_SHORT).show()
//            }else{
//                viewModel.addSubscribers(coffeePost.userNickname!!)
//                viewModel.getMySubsLiveData().value?.add(coffeePost.userNickname!!)
//            }
//        }


        Glide.with(requireContext())
            .load(viewModel.getImageRef(coffeePost.id!!))
            .transform(RoundedCorners(20))
            .error(R.drawable.on_error)
            .into(mBinding.coffeeRecipePic)

        mBinding.btnAdd.setOnClickListener {
            if (viewModel.getSubOrUnsub()!!) {
                viewModel.addSubscribers(coffeePost.userNickname!!)
                viewModel.getMySubsList().add(coffeePost.userNickname!!)
                viewModel.setSubOrUnsub(false)
                mBinding.btnAdd.setImageResource(R.drawable.delete_icon)
                Toast.makeText(context, "Вы подписались на пользователя", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.setSubOrUnsub(true)
                viewModel.unsubUser(coffeePost.userNickname!!)
                mBinding.btnAdd.setImageResource(R.drawable.baseline_add_24)
                Toast.makeText(context, "Вы отписались от пользователя", Toast.LENGTH_SHORT).show()
            }
        }

        val isContain=viewModel.getMySubsList().contains(coffeePost.userNickname)
        if(isContain) {
            viewModel.setSubOrUnsub(false)
            mBinding.btnAdd.setImageResource(R.drawable.delete_icon)
        }



        mBinding.btnLike.setOnClickListener {
            if (!viewModel.getIsLiked()) {
                viewModel.addFavoritePost(coffeePost.id!!)
                viewModel.getFavoritePostID().add(coffeePost.id!!)
                viewModel.setIsLiked(true)
                mBinding.btnLike.setImageResource(R.drawable.favorite_active)
                Toast.makeText(context, "Вы сохранили пост", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.setIsLiked(false)
                mBinding.btnLike.setImageResource(R.drawable.favorite_icon)

                Toast.makeText(context, "Вы удалили пост", Toast.LENGTH_SHORT).show()
            }
        }

        val isLiked=viewModel.getFavoritePostID().contains(coffeePost.id)
        if(isLiked) {
            viewModel.setIsLiked(true)
            mBinding.btnLike.setImageResource(R.drawable.favorite_active)
        }
    }






    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }



}