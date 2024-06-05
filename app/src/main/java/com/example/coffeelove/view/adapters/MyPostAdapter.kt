package com.example.coffeelove.view.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffeelove.R
import com.example.coffeelove.view.fragment.account.AccountFragment
import com.example.coffeelove.view.fragment.account.MyAccountFragment
import com.example.coffeelove.view.fragment.coffee.CoffeePost
import com.example.coffeelove.viewModel.CoffeeViewModel
import com.example.coffeelove.view.fragment.coffee.MyDiffUtil
import com.example.coffeelove.databinding.CoffeePostBinding

class MyPostAdapter(
    private val fragment:Fragment,
    private val viewModel: CoffeeViewModel
) :RecyclerView.Adapter<MyPostAdapter.ViewHolder>() {


    var myPostList = listOf<CoffeePost>()
        set(value) {
            if (field.size != value.size) {
                val callback = MyDiffUtil(oldArray = field, newArray = value,
                    { old, new ->
                        Log.d("RRR", "${old.id} | ${new.id} ")
                        return@MyDiffUtil old.id == new.id
                    })
                field = value
                val diffResult = DiffUtil.calculateDiff(callback)
                diffResult.dispatchUpdatesTo(this)
            }
        }


    @SuppressLint("NotifyDataSetChanged")
    fun notifying(){
        notifyDataSetChanged()
    }

    class ViewHolder(item: View):RecyclerView.ViewHolder(item){

        private var _bindig: CoffeePostBinding
        val mBinding get() = _bindig
        init {
            _bindig= CoffeePostBinding.bind(item)
        }

        private val recipeName=item.findViewById<TextView>(R.id.recipe_name)
        private val userNickname=item.findViewById<TextView>(R.id.user_name)

        fun onBind(items: CoffeePost){
            recipeName.text=items.recipeName
            userNickname.text=items.userNickname
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.coffee_post, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (fragment is AccountFragment){
            holder.mBinding.btnDeleteOrAdd.visibility=View.GONE
        }
        holder.onBind(myPostList[position])
        Glide.with(fragment.requireContext())
            .load(viewModel.getImageRef(myPostList[position].id!!))
            .placeholder(R.drawable.on_load)
            .error(R.drawable.on_error)
            .into(holder.mBinding.coffeRecipePic)
//        holder.mBinding.recipeDescription.setOnClickListener{
//            viewModel.setMoreCoffeePostAboutFragment(myPostList[position])
//            if (fragment is MyAccountFragment){
//                fragment.findNavController().navigate(R.id.action_myAccountFragment_to_coffeePostAboutFragment2)
//            }else{
//                fragment.findNavController().navigate(R.id.action_accountFragment_to_coffeePostAboutFragment)
//            }
//        }
        holder.mBinding.coffePost.setOnClickListener {
            if(it!=holder.mBinding.btnDeleteOrAdd && it!=holder.mBinding.userIcon){
                viewModel.setMoreCoffeePostAboutFragment(myPostList[position])
                if (fragment is MyAccountFragment){
                    fragment.findNavController().navigate(R.id.action_myAccountFragment_to_coffeePostAboutFragment2)
                }
                else{
                    fragment.findNavController().navigate(R.id.coffeePostAboutFragment)
                }
            }
        }
    }


    override fun getItemCount()= myPostList.size
    }



