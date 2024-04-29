package com.example.coffeelove.coffee

import android.annotation.SuppressLint
import android.util.Log
import android.view.ContextMenu
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




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater=LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.coffee_post, parent, false))
    }

    override fun getItemCount()=notesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mBinding.btnDeleteOrAdd.setImageResource(R.drawable.baseline_add_24)
        holder.onBind(notesList[position])

        Glide.with(fragment.requireContext())
            .load(viewModel.getImageRef(22))
            .placeholder(R.drawable.on_load)
            .error(R.drawable.on_error)
            .into(holder.mBinding.coffeRecipePic)

        holder.mBinding.recipeDescription.setOnClickListener {
            viewModel.setMoreCoffeePostAboutFragment(notesList.get(position))
            fragment.findNavController().navigate(R.id.action_coffeeFragment_to_coffeePostAboutFragment)
        }

        holder.mBinding.userIcon.setOnClickListener {
            viewModel.downLoadOpenUser(notesList[position].userNickname!!)
            it.findNavController().navigate(R.id.action_coffeeFragment_to_accountFragment)
        }

        holder.mBinding.btnLike.setOnClickListener {
            val isContain=viewModel.getFavoritePostID().contains(notesList[position].id)
            Log.d("FENIXXX",isContain.toString())
            if(isContain){
                Toast.makeText(fragment.context,"Пост уже добавлен в избранное",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.addFavoritePost(notesList[position].id!!)
                viewModel.getLiveDataFavorite().value?.add(notesList[position])
            }

        }


        holder.mBinding.btnDeleteOrAdd.setOnClickListener {
            val isContain= viewModel.getMySubsLiveData().value?.contains(notesList[position].userNickname)
            if(isContain==true){
                Toast.makeText(fragment.context,"Вы уже подписаны",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.addSubscribers(notesList[position].userNickname!!)
                viewModel.getMySubsLiveData().value?.add(notesList[position].userNickname!!)
            }

        }

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