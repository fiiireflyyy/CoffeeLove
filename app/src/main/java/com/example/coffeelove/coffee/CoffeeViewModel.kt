package com.example.coffeelove.coffee

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coffeelove.data.repository.Repository

class CoffeeViewModel : ViewModel() {

    val listLiveData: MutableLiveData<ArrayList<CoffeePost>> by lazy { MutableLiveData<ArrayList<CoffeePost>>() }

    private val repository=Repository()


    init {
        listLiveData.value=ArrayList()
    }

    fun createPost(id: Int, recipeName : String, recipeDescription:String){
        repository.createPost(id,"Test",1234, recipeName,recipeDescription)
//        listLiveData.value=listLiveData.value.orEmpty()+CoffeePost(id,"Test",1234, recipeName,recipeDescription)
    }
//    fun refresh(id: Int, recipeName : String, recipeDescription:String){
//        listLiveData.value=listLiveData.value.orEmpty()+CoffeePost(id,"Test",1234, recipeName,recipeDescription)
//    }

    fun refresh(id: Int, recipeName : String, recipeDescription:String){
        repository.upLoadAllPosts(listLiveData)
    }

    fun getListLiveData(): ArrayList<CoffeePost>? {
        return listLiveData.value
    }







}