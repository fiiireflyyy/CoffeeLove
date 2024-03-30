package com.example.coffeelove.coffee

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
import com.example.coffeelove.R
import com.example.coffeelove.account.AccountFragment
import com.example.coffeelove.account.MyAccountFragment
import com.example.coffeelove.databinding.CoffeePostBinding
import com.example.coffeelove.databinding.FragmentAccountBinding

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
        private val recipeDescription=item.findViewById<TextView>(R.id.recipe_description)
        private val userNickname=item.findViewById<TextView>(R.id.user_name)
        private val countLike=item.findViewById<TextView>(R.id.countLike)

        fun onBind(items:CoffeePost){
            recipeName.text=items.recipeName
            recipeDescription.text=items.recipeDescription
            userNickname.text=items.userNickname
            countLike.text=items.countLike.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.coffee_post, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(myPostList[position])
        holder.mBinding.recipeDescription.setOnClickListener{
            viewModel.setMoreCoffeePostAboutFragment(myPostList[position])
            if (fragment is MyAccountFragment){
                fragment.findNavController().navigate(R.id.action_myAccountFragment_to_coffeePostAboutFragment2)
            }else{
                fragment.findNavController().navigate(R.id.action_accountFragment_to_coffeePostAboutFragment)
            }
        }

        if(fragment is AccountFragment){
            holder.mBinding.btnLike.setOnClickListener {
                val isContain=viewModel.getFavoritePostID().contains(myPostList[position].id)
                if(isContain){
                    Toast.makeText(fragment.context,"Пост уже добавлен в избранное",Toast.LENGTH_SHORT).show()
                }else{
                    viewModel.addFavoritePost(myPostList[position].id!!)
                    viewModel.getLiveDataFavorite().value?.add(myPostList[position])
                }
            }
        }
    }


    override fun getItemCount()= myPostList.size
    }



