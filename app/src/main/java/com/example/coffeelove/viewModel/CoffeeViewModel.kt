package com.example.coffeelove.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coffeelove.view.fragment.coffee.CoffeePost
import com.example.coffeelove.model.repository.Repository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.StorageReference

class CoffeeViewModel : ViewModel() {
    //Не нужен так как перенес все хранение в repositiry

    private val repository=Repository()
    private var moreCoffeePostAboutFragment: CoffeePost? =null


    private var imageUri: Uri?=null




    init {
        repository.llistLiveData.observeForever {
            Log.d("FENIX","Обновилось в фоне")
        }

    }

    fun setLastUriImage(uri:Uri){
        imageUri=uri
    }


    fun getImageRef(id:Long): StorageReference {
        return repository.getImageRef(id)
    }
    //УСТАНОВКА ТЕКУЩЕГО ЮЗЕРА




    fun setCurrentUser(user: FirebaseUser?){
        repository.setCurrentUser(user!!)
    }
    fun getCurrentUserName():String{
        return repository.getCurrentUserName()
    }
    //Отправка данных о новом зарегистрированном пользователе
    fun createUser(newUser: FirebaseUser?) {
        repository.createUser(newUser)
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
        repository.createPost(id,1234, recipeName,recipeDescription,imageUri)
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

    //ФУНКЦИОНАЛ ЛЕНТЫ ДЛЯ ВАС
    fun downLoadPersonal(){
        repository.downLoadPersonal()
    }

    fun getRecomendLive(): MutableLiveData<ArrayList<CoffeePost>> {
        return repository.getRecomendLive()
    }



}