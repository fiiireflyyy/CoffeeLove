package com.example.coffeelove.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffeelove.R
import com.example.coffeelove.view.fragment.account.MyAccountFragment
import com.example.coffeelove.view.fragment.coffee.CoffeePost
import com.example.coffeelove.viewModel.CoffeeViewModel
import com.example.coffeelove.view.fragment.coffee.MyDiffUtil
import com.example.coffeelove.databinding.CoffeePostBinding

class FavoritePostAdapter(
    private val fragment: MyAccountFragment,
    private val viewModel: CoffeeViewModel
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
    ): ViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.coffee_post, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(myFavoriteList[position])
        Glide.with(fragment.requireContext())
            .load(viewModel.getImageRef(myFavoriteList[position].id!!))
            .placeholder(R.drawable.on_load)
            .error(R.drawable.on_error)
            .into(holder.mBinding.coffeRecipePic)

        holder.mBinding.userIcon.setOnClickListener {
            viewModel.setGoToUser(myFavoriteList[position].userNickname!!)
            viewModel.downLoadOpenUser(myFavoriteList[position].userNickname!!)
            it.findNavController().navigate(R.id.action_myAccountFragment_to_accountFragment)
        }
        holder.mBinding.coffePost.setOnClickListener {
            if(it!=holder.mBinding.btnDeleteOrAdd && it!=holder.mBinding.userIcon){
                viewModel.setMoreCoffeePostAboutFragment(myFavoriteList.get(position))
                fragment.findNavController().navigate(R.id.coffeePostAboutFragment)
            }
        }
        holder.mBinding.btnDeleteOrAdd.setOnClickListener {
            val local=viewModel.getLiveDataFavorite().value
            local!!.removeAt(position)
            viewModel.getLiveDataFavorite().postValue(local)
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
        private val userNickname=item.findViewById<TextView>(R.id.user_name)

        fun onBind(items: CoffeePost){
            recipeName.text=items.recipeName
            userNickname.text=items.userNickname
        }
    }
}