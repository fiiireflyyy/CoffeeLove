package com.example.coffeelove.coffee

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeelove.R
import com.example.coffeelove.databinding.CoffeePostBinding

class RecyclerAdapter(
    private val fragment:CoffeeFragment,
    private val viewModel:CoffeeViewModel):RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var notesList = listOf<CoffeePost>()
        set(value) {
            val callback = MyDiffUtil(oldArray = field, newArray = value,
                {old, new ->  old.id==new.id})
            field = value
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
        }
    var posit : Int = -1




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater=LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.coffee_post, parent, false))
    }

    override fun getItemCount()=notesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(notesList[position])
        holder.mBinding.recipeDescription.setOnClickListener {
            viewModel.setMoreCoffeePostAboutFragment(notesList.get(position))
            fragment.findNavController().navigate(R.id.action_coffeeFragment_to_coffeePostAboutFragment)
        }

        holder.mBinding.userIcon.setOnClickListener {
            viewModel.downLoadOpenUser(notesList[position].userNickname!!)
            it.findNavController().navigate(R.id.action_coffeeFragment_to_accountFragment)
        }

    }


    class ViewHolder(item: View):RecyclerView.ViewHolder(item){

        private var _binding:CoffeePostBinding
        val mBinding get()=_binding

        init {
            _binding=CoffeePostBinding.bind(item)
        }


        private  val recipeName=item.findViewById<TextView>(R.id.recipe_name)
        private val recipeDescription=item.findViewById<TextView>(R.id.recipe_description)
        private val userNickname=item.findViewById<TextView>(R.id.user_name)
        private val countLike=item.findViewById<TextView>(R.id.countLike)
        fun onBind(items: CoffeePost){
            userNickname.text=items.userNickname
            countLike.text=items.countLike.toString()
            recipeName.text=items.recipeName
            recipeDescription.text=items.recipeDescription
        }

    }

}