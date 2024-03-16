package com.example.coffeelove.coffee

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coffeelove.data.repository.Repository
import com.example.coffeelove.databinding.CoffeePostBinding

class CoffeeViewModel : ViewModel() {
    //Не нужен так как перенес все хранение в repositiry
    val listLiveData: MutableLiveData<ArrayList<CoffeePost>> by lazy { MutableLiveData<ArrayList<CoffeePost>>() }

    private val repository=Repository()
    private var moreCoffeePostAboutFragment: CoffeePost? =null





    init {
        listLiveData.value=ArrayList()
        repository.llistLiveData.observeForever {
            Log.d("FENIX","Обновилось в фоне")
        }

    }

    fun getMoreCoffeePostAboutFragment(): CoffeePost? {
        return moreCoffeePostAboutFragment
    }
    fun setMoreCoffeePostAboutFragment(post: CoffeePost){
        moreCoffeePostAboutFragment=post
    }



    fun createPost(id: Int, recipeName : String, recipeDescription:String){
        repository.createPost(id,"Test",1234, recipeName,recipeDescription)
//        listLiveData.value=listLiveData.value.orEmpty()+CoffeePost(id,"Test",1234, recipeName,recipeDescription)
    }
//    fun refresh(id: Int, recipeName : String, recipeDescription:String){
//        listLiveData.value=listLiveData.value.orEmpty()+CoffeePost(id,"Test",1234, recipeName,recipeDescription)
//    }

    //Было нужно в самом начале для теста
    //Потребуется при добавлении поста в разделе мои посты,но н
    fun refresh(id: Int, recipeName : String, recipeDescription:String){
        repository.upLoadAllPosts(listLiveData)
    }

    fun getListLiveData(): ArrayList<CoffeePost>? {
        return repository.llistLiveData.value
    }

    fun addBackGroundUppLoad(){
        repository.backGroundLoadAllPosts()
    }







}