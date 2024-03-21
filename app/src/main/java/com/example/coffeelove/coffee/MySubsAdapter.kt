package com.example.coffeelove.coffee

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeelove.R
import com.example.coffeelove.account.MyAccountFragment
import com.example.coffeelove.databinding.ItemSubscriberBinding

class MySubsAdapter(
    private val fragment:MyAccountFragment,
    private val viewModel:CoffeeViewModel): RecyclerView.Adapter<MySubsAdapter.ViewHolder>(){
    var mySubsList = listOf<String>()
//        set(value) {
//            Log.d("FENIXXX",mySubsList.toString())
//            val callback = MyDiffUtil(oldArray = field, newArray = value,
//                {old, new ->  old.id==new.id})
//            field = value
//            val diffResult = DiffUtil.calculateDiff(callback)
//            diffResult.dispatchUpdatesTo(this)
//        }



    class ViewHolder(item: View):RecyclerView.ViewHolder(item){
        private var _binding:ItemSubscriberBinding
        val mBinding get()=_binding

        init {
            _binding=ItemSubscriberBinding.bind(item)
        }
        private val userNameSub=item.findViewById<TextView>(R.id.user_name_sub)

        fun onBind(userName:String){
            userNameSub.text=userName
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySubsAdapter.ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_subscriber,parent,false))
    }

    override fun onBindViewHolder(holder: MySubsAdapter.ViewHolder, position: Int) {
        holder.onBind(mySubsList[position])

        holder.mBinding.profileIcon.setOnClickListener {
            viewModel.downLoadOpenUser(mySubsList[position])
            it.findNavController().navigate(R.id.action_myAccountFragment_to_accountFragment)
        }
        holder.mBinding.showButton.setOnClickListener {
            viewModel.downLoadOpenUser(mySubsList[position])
            it.findNavController().navigate(R.id.action_myAccountFragment_to_accountFragment)
        }

    }

    override fun getItemCount()=mySubsList.size
    }

