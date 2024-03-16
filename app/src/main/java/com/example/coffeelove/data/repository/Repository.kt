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

class Repository {

    //Хранение всех постов, закаченных с бд для передачи в ленту
    val llistLiveData: MutableLiveData<ArrayList<CoffeePost>> by lazy { MutableLiveData<ArrayList<CoffeePost>>()}
    private val database=Firebase.database.reference
    private val firebaseRef=FirebaseDatabase.getInstance().getReference("posts")

    //Есть ли слушатель на обновление ленты постов
//    private var checkAddListener=false

    init {
        llistLiveData.value=ArrayList<CoffeePost>()
    }


    //Установка слушателя для фоновой подкачки
    fun backGroundLoadAllPosts(){
            firebaseRef.addValueEventListener(object :ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("FENIXX","Фоновые изменения")
                    if (snapshot.exists()){
                        for(postsCoffee in snapshot.children){
                            val coffeePost=postsCoffee.getValue(CoffeePost::class.java)
                            llistLiveData.value?.add(coffeePost!!)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("FireFly","Ошибка подгрузки")
                }
            })

        }


    //Нужно было для теста
    fun upLoadAllPosts(listLiveData: MutableLiveData<ArrayList<CoffeePost>>){
        firebaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
//                postsLists.clear()
                var copyListLiveData=listLiveData.value
                copyListLiveData=ArrayList<CoffeePost>()
                if(snapshot.exists()){
                    for(postsCoffee in snapshot.children){
                        val coffeePosts=postsCoffee.getValue(CoffeePost::class.java)
                        copyListLiveData.add(coffeePosts!!)
                        Log.d("COFFEEPOST",coffeePosts!!.id.toString())
//                        postsLists.add(coffeePosts!!)
                        listLiveData.postValue(copyListLiveData)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("PRON", "Ошибка загрузки")
            }
        })
    }

    //Функция отправления поста в базу данных
    fun createPost(
        id: Int,
        userNickName:String,
        countLike:Long,
        recipeName:String,
        recipeDescription:String){


        val coffeePost=CoffeePost(id,userNickName,countLike, recipeName,recipeDescription)
        database.child("posts").child(id.toString()).setValue(coffeePost)

    }


}