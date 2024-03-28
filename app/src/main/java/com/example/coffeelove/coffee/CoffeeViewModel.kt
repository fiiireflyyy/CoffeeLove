package com.example.coffeelove.coffee

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coffeelove.data.repository.Repository
import com.example.coffeelove.databinding.CoffeePostBinding

class CoffeeViewModel : ViewModel() {
    //Не нужен так как перенес все хранение в repositiry

    private val repository=Repository()
    private var moreCoffeePostAboutFragment: CoffeePost? =null





    init {
        repository.llistLiveData.observeForever {
            Log.d("FENIX","Обновилось в фоне")
        }

    }

    fun getListTest(): MutableLiveData<ArrayList<CoffeePost>> {
        return repository.getListTest()
    }

    fun getMoreCoffeePostAboutFragment(): CoffeePost? {
        return moreCoffeePostAboutFragment
    }
    fun setMoreCoffeePostAboutFragment(post: CoffeePost){
        moreCoffeePostAboutFragment=post
    }



    fun createPost(id: Long, recipeName: String, recipeDescription:String){
        repository.createPost(id,"TestUser",1234, recipeName,recipeDescription)
    }



    fun getListLiveData(): ArrayList<CoffeePost>? {
        return repository.llistLiveData.value
    }

    fun addBackGroundUppLoad(){
        repository.backGroundLoadAllPosts()
    }


    fun getMyPostFromBase(){
        repository.getMyPostFromBase()
    }
    fun getMyPostLiveData(): MutableLiveData<ArrayList<CoffeePost>> {
        return repository.getMyPostListLive()
    }

    fun getId(): Long {
        return repository.getGenerateId()
    }


    //Функционал просмотра профиля другого пользователя

    fun downLoadOpenUser(userName: String){
        repository.getInformationAboutUser(userName)
    }

    fun getLiveDataOpenUser(): MutableLiveData<ArrayList<CoffeePost>> {
        return repository.getLiveDataOpenUser()
    }


    //фУНКЦИОНАЛЬНОСТЬ ЛАЙКНУТЫХ ПОСТОВ

    fun getFavoritePostID():ArrayList<Long>{
        return repository.getFavoritePostID()
    }
    fun downLoadFavorite(){
        repository.downLoadFavoritePosts()
    }
    fun getLiveDataFavorite(): MutableLiveData<ArrayList<CoffeePost>> {
        return repository.getLiveDataFavorite()
    }

    fun addFavoritePost(postId: Long){
        repository.addPostFavorite(postId)
    }

    //ФУНКЦИОНАЛ ПОДПИСКИ
    fun addSubscribers(subName:String){
        repository.addSubscribers(subName)
    }
    fun getMySubs(){
        repository.getMySubs()
    }
    fun getMySubsLiveData():MutableLiveData<ArrayList<String>>{
        return repository.getSubsLiveData()
    }





}