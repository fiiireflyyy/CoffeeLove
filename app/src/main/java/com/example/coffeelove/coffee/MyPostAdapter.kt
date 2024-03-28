package com.example.coffeelove.coffee

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeelove.R

class MyPostAdapter :RecyclerView.Adapter<MyPostAdapter.ViewHolder>() {


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
    }


    override fun getItemCount()= myPostList.size
    }



