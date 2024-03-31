package com.example.coffeelove.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.coffeelove.coffee.CoffeePost
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Repository {

    //Хранение всех постов, закаченных с бд для передачи в ленту
    val llistLiveData: MutableLiveData<ArrayList<CoffeePost>> by lazy { MutableLiveData<ArrayList<CoffeePost>>()}
    private val testList=ArrayList<CoffeePost>()
    private val database=Firebase.database.reference
    //Ссылка на все посты
    private val firebaseRefPosts=FirebaseDatabase.getInstance().getReference("posts")

    //Ссылка на пользователя
    private val userRef=database.child("Users").child("TestUser")

    //Массив моих постов
    private val myPostList= ArrayList<CoffeePost>()
    private val myPostLiveData:MutableLiveData<ArrayList<CoffeePost>> by lazy { MutableLiveData<ArrayList<CoffeePost>>() }

    //Массив постов открытого пользователя
    private val openUserPosts: MutableLiveData<ArrayList<CoffeePost>> by lazy { MutableLiveData<ArrayList<CoffeePost>>()}


    //Массив избранных постов
    private val favoritePosts:MutableLiveData<ArrayList<CoffeePost>> by lazy { MutableLiveData<ArrayList<CoffeePost>>() }
    private val favoritePostsID= ArrayList<Long>()

    //Хранение моих подписок
    private val mySubsLiveData:MutableLiveData<ArrayList<String>> by lazy { MutableLiveData<ArrayList<String>>() }
    private val mySubsNameString=ArrayList<String>()


    //Хранение постов для вас.
    private val myRecomendLive:MutableLiveData<ArrayList<CoffeePost>> by lazy { MutableLiveData<ArrayList<CoffeePost>>() }
    private val myRecomendCopy=ArrayList<CoffeePost>()



    init {
        llistLiveData.value=ArrayList<CoffeePost>()
    }

    fun getListTest(): MutableLiveData<ArrayList<CoffeePost>> {
        return llistLiveData
    }

    //Установка слушателя для фоновой подкачки
    fun backGroundLoadAllPosts(){
            firebaseRefPosts.addValueEventListener(object :ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    testList.clear()
                    llistLiveData.value?.clear()
                    Log.d("FENIXX","Фоновые изменения")
                    if (snapshot.exists()){
                        for(postsCoffee in snapshot.children){
                            val coffeePost=postsCoffee.getValue(CoffeePost::class.java)
                            testList.add(coffeePost!!)
                            llistLiveData.postValue(testList)
//                            llistLiveData.value?.add(coffeePost!!)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("FireFly","Ошибка подгрузки")
                }
            })

        }



    //Функция отправления поста в базу данных
    fun createPost(
        id: Long,
        userNickName:String,
        countLike:Long,
        recipeName:String,
        recipeDescription:String){

        val coffeePost=CoffeePost(id,userNickName,countLike, recipeName,recipeDescription)
        myPostList.add(coffeePost)
        myPostLiveData.postValue(myPostList)
        Firebase.database.getReference("Users/$userNickName/MyPost/${id.toString()}").setValue(id)
        database.child("posts").child(id.toString()).setValue(coffeePost)
    }


    fun getMyPostFromBase(){
        myPostList.clear()
        userRef.child("MyPost").get().addOnSuccessListener {
            for (myAddPost in it.children){
                val coffeePostID=myAddPost.getValue(Long::class.java)
                firebaseRefPosts.child(coffeePostID.toString()).get().addOnSuccessListener {post->
                    val coffeePost=post.getValue(CoffeePost::class.java)
                    myPostList.add(coffeePost!!)
                    myPostLiveData.postValue(myPostList)
                }
            }
        }
    }



    //Функция выдачи списка постов
    fun getMyPostListLive(): MutableLiveData<ArrayList<CoffeePost>> {
        return myPostLiveData
    }

    fun getGenerateId(): Long {
        val sdf=SimpleDateFormat("yyyMMddHHmmss")
        val id=sdf.format(Date())
        Log.d("FENIXXX",id)
        return id.toLong()
    }


    //Запрос информации о юзере.
    fun getInformationAboutUser(namePath:String){
        Log.d("FENIXXX",namePath)
        val openUserList=ArrayList<CoffeePost>()
        database.child("Users").child(namePath).child("MyPost").get().addOnSuccessListener {
            for (postUser in it.children){
                val coffeePostId=postUser.getValue(Long::class.java)
                database.child("posts").child(coffeePostId.toString()).get().addOnSuccessListener {post->
                    val coffeePost=post.getValue(CoffeePost::class.java)
                    openUserList.add(coffeePost!!)
                    openUserPosts.postValue(openUserList)
                }

            }
        }
    }
    fun getLiveDataOpenUser(): MutableLiveData<ArrayList<CoffeePost>> {
        return openUserPosts
    }





    //Запросы информации о добавленных в избранное постах
    fun downLoadFavoritePosts(){
        favoritePostsID.clear()
        val favoritePostList=ArrayList<CoffeePost>()
        database.child("Users").child("TestUser").child("Favorite").get().addOnSuccessListener {
            for (favoritePost in it.children){
                val coffeePostID=favoritePost.getValue(Long::class.java)
                favoritePostsID.add(coffeePostID!!)
                firebaseRefPosts.child(coffeePostID.toString()).get().addOnSuccessListener {post->
                    val coffeePost=post.getValue(CoffeePost::class.java)
                    favoritePostList.add(coffeePost!!)
                    favoritePosts.postValue(favoritePostList)
                }
            }
        }
    }

    fun getFavoritePostID():ArrayList<Long>{
        return favoritePostsID
    }
    fun getLiveDataFavorite(): MutableLiveData<ArrayList<CoffeePost>> {
        return favoritePosts
    }

    //Добавление поста в избранное.
    fun addPostFavorite(postId: Long){
        favoritePostsID.add(postId)
        Firebase.database.getReference("Users/TestUser/Favorite/${getGenerateId()}").setValue(postId)
        userRef.child("Favorite").child(getGenerateId().toString()).setValue(postId)
    }



    //Функционал ПОДПИСОК
    fun addSubscribers(subName:String){
        Firebase.database.getReference("Users/TestUser/Subs/${getGenerateId()}").setValue(subName)
    }

    fun getMySubs(){
        mySubsNameString.clear()
        userRef.child("Subs").get().addOnSuccessListener {
            for(nameSubs in it.children){
                val nameSub=nameSubs.getValue(String::class.java)
                mySubsNameString.add(nameSub!!)
                mySubsLiveData.postValue(mySubsNameString)
            }
        }
    }

    fun getMySubsList():ArrayList<String>{
        return mySubsNameString
    }
    fun getSubsLiveData():MutableLiveData<ArrayList<String>>{
        return mySubsLiveData
    }



    //ЛОГИКА ЛЕНТЫ ДЛЯ ВАС

    fun downLoadPersonal(){
        myRecomendCopy.clear()
        userRef.child("Subs").get().addOnSuccessListener {
            for(mySub in it.children){
                val sub=mySub.getValue(String::class.java)
                database.child("Users").child(sub!!).child("MyPost").get().addOnSuccessListener {posts->
                    for(post in posts.children){
                        val coffeePostID=post.getValue(Long::class.java)
                        database.child("posts").child(coffeePostID.toString()).get().addOnSuccessListener {
                            val coffeePost=it.getValue(CoffeePost::class.java)
                            myRecomendCopy.add(coffeePost!!)
                        }
                    }
                }
            }
        }
        Log.d("RRR","notify1 ${myRecomendCopy}")

        myRecomendCopy.sortWith(compareBy{it.id})
        myRecomendLive.postValue(myRecomendCopy)
        Log.d("RRR","notify ${myRecomendCopy}")
    }

    fun getRecomendLive():MutableLiveData<ArrayList<CoffeePost>>{
        return myRecomendLive
    }



}