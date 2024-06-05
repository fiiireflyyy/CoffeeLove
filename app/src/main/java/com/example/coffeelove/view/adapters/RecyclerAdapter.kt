package com.example.coffeelove.view.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffeelove.R
import com.example.coffeelove.view.fragment.coffee.CoffeeFragment
import com.example.coffeelove.view.fragment.coffee.CoffeePost
import com.example.coffeelove.viewModel.CoffeeViewModel
import com.example.coffeelove.view.fragment.coffee.MyDiffUtil
import com.example.coffeelove.databinding.CoffeePostBinding

class RecyclerAdapter(
    private val fragment: CoffeeFragment,
    private val viewModel: CoffeeViewModel
):RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var notesList = listOf<CoffeePost>()
        set(value) {
            val callback = MyDiffUtil(oldArray = field, newArray = value,
                {old, new ->  old.id==new.id})
            field = value
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
        }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater=LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.coffee_post, parent, false))
    }

    override fun getItemCount()=notesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mBinding.btnDeleteOrAdd.visibility=View.GONE
        holder.onBind(notesList[position])

        Glide.with(fragment.requireContext())
            .load(viewModel.getImageRef(notesList[position].id!!))
            .placeholder(R.drawable.on_load)
            .error(R.drawable.on_error)
            .into(holder.mBinding.coffeRecipePic)

//        holder.mBinding.recipeDescription.setOnClickListener {
//            viewModel.setMoreCoffeePostAboutFragment(notesList.get(position))
//            fragment.findNavController().navigate(R.id.action_coffeeFragment_to_coffeePostAboutFragment)
//        }
        holder.mBinding.coffePost.setOnClickListener {
            if(it!=holder.mBinding.btnDeleteOrAdd && it!=holder.mBinding.userIcon){
                viewModel.setMoreCoffeePostAboutFragment(notesList.get(position))
                fragment.findNavController().navigate(R.id.action_coffeeFragment_to_coffeePostAboutFragment)
            }
        }

        holder.mBinding.userIcon.setOnClickListener {
            viewModel.setGoToUser(notesList[position].userNickname!!)
            viewModel.downLoadOpenUser(notesList[position].userNickname!!)
            it.findNavController().navigate(R.id.action_coffeeFragment_to_accountFragment)
        }

//        holder.mBinding.btnLike.setOnClickListener {
//            val isContain=viewModel.getFavoritePostID().contains(notesList[position].id)
//            Log.d("FENIXXX",isContain.toString())
//            if(isContain){
//                Toast.makeText(fragment.context,"Пост уже добавлен в избранное",Toast.LENGTH_SHORT).show()
//            }else{
//                viewModel.addFavoritePost(notesList[position].id!!)
//                viewModel.getLiveDataFavorite().value?.add(notesList[position])
//            }
//
//        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifying(){
        notifyDataSetChanged()
    }


    class ViewHolder(item: View):RecyclerView.ViewHolder(item){

        private var _binding:CoffeePostBinding
        val mBinding get()=_binding

        init {
            _binding=CoffeePostBinding.bind(item)
        }


        private  val recipeName=item.findViewById<TextView>(R.id.recipe_name)
        private val userNickname=item.findViewById<TextView>(R.id.user_name)
        fun onBind(items: CoffeePost){
            userNickname.text=items.userNickname
            recipeName.text=items.recipeName
        }

    }

}