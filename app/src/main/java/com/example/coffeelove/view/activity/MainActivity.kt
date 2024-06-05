package com.example.coffeelove.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.coffeelove.R
import com.example.coffeelove.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    public fun navRestart(){
        val navTopestController = (supportFragmentManager
            .findFragmentById(R.id.mainGraphContainer) as NavHostFragment)
            .navController
        navTopestController.navigate(R.id.loginFragment)
    }
}