package com.example.coffeelove.coffee

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeelove.R
import com.example.coffeelove.account.MyAccountFragment
import com.example.coffeelove.databinding.CoffeePostBinding

class FavoritePostAdapter(
    private val fragment:MyAccountFragment,
    private val viewModel:CoffeeViewModel
): RecyclerView.Adapter<FavoritePostAdapter.ViewHolder>() {


    var myFavoriteList = listOf<CoffeePost>()
        set(value) {
            val callback = MyDiffUtil(oldArray = field, newArray = value,
                {old, new ->  old.id==new.id})
            field = value
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
        }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritePostAdapter.ViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        return FavoritePostAdapter.ViewHolder(inflater.inflate(R.layout.coffee_post, parent, false))
    }

    override fun onBindViewHolder(holder: FavoritePostAdapter.ViewHolder, position: Int) {
        holder.onBind(myFavoriteList[position])

        holder.mBinding.userIcon.setOnClickListener {
            viewModel.downLoadOpenUser(myFavoriteList[position].userNickname!!)
            it.findNavController().navigate(R.id.action_myAccountFragment_to_accountFragment)
        }
    }

    override fun getItemCount()=myFavoriteList.size



    @SuppressLint("NotifyDataSetChanged")
    fun notifying(){
        notifyDataSetChanged()
    }

    class ViewHolder(item: View):RecyclerView.ViewHolder(item){

        private var _binding:CoffeePostBinding

        val mBinding get()=_binding
        init {
            _binding= CoffeePostBinding.bind(item)
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
}