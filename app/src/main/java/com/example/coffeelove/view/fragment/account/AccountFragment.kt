package com.example.coffeelove.view.fragment.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeelove.R
import com.example.coffeelove.viewModel.CoffeeViewModel
import com.example.coffeelove.view.adapters.MyPostAdapter
import com.example.coffeelove.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {


    private var _binding: FragmentAccountBinding?=null
    private val viewModel: CoffeeViewModel by activityViewModels<CoffeeViewModel>()
    private lateinit var openRecyclerAdapter: MyPostAdapter
    private val mBinding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentAccountBinding.inflate(inflater, container, false)

        mBinding.buttonGoBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        openRecyclerAdapter= MyPostAdapter(this, viewModel)
        mBinding.recyclerOpenAccount.layoutManager=LinearLayoutManager(context)
        mBinding.recyclerOpenAccount.adapter=openRecyclerAdapter

        viewModel.getLiveDataOpenUser().observe(
            viewLifecycleOwner,
        ){
            array->openRecyclerAdapter.myPostList=array
        }

        mBinding.btnAdd.setOnClickListener {
            if (viewModel.getSubOrUnsub()!!) {
                viewModel.addSubscribers(viewModel.getGoToUser())
                viewModel.getMySubsList().add(viewModel.getGoToUser())
                viewModel.setSubOrUnsub(false)
                mBinding.btnAdd.setImageResource(R.drawable.delete_icon)
                Toast.makeText(context, "Вы подписались на пользователя", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.setSubOrUnsub(true)
                viewModel.unsubUser(viewModel.getGoToUser())
                mBinding.btnAdd.setImageResource(R.drawable.baseline_add_24)
                Toast.makeText(context, "Вы отписались от пользователя", Toast.LENGTH_SHORT).show()
            }
        }

        val isContain=viewModel.getMySubsList().contains(viewModel.getGoToUser())
        if(isContain) {
            viewModel.setSubOrUnsub(false)
            mBinding.btnAdd.setImageResource(R.drawable.delete_icon)
        }



        return mBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}