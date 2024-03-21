package com.example.coffeelove.coffee

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeelove.R

class FavoritePostAdapter: RecyclerView.Adapter<FavoritePostAdapter.ViewHolder>() {


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
    }

    override fun getItemCount()=myFavoriteList.size




    class ViewHolder(item: View):RecyclerView.ViewHolder(item){
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