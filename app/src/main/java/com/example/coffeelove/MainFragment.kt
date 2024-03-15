package com.example.coffeelove

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.coffeelove.databinding.FragmentLoginBinding
import com.example.coffeelove.databinding.FragmentMainBinding
import com.google.android.material.navigation.NavigationBarView

class MainFragment : Fragment() {


    private var _binding: FragmentMainBinding?=null
    private val mBinding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentMainBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController  =(childFragmentManager.findFragmentById(R.id.main_nav_container)
                as NavHostFragment).navController
        NavigationUI.setupWithNavController(view.findViewById(R.id.bottom_navigation_view)
                as NavigationBarView, navController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}