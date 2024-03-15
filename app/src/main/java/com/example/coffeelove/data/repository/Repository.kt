package com.example.coffeelove.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.coffeelove.coffee.CoffeePost
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Repository {


    private val llistLiveData: MutableLiveData<ArrayList<CoffeePost>> by lazy { MutableLiveData<ArrayList<CoffeePost>>()}
    private val database=Firebase.database.reference
    private val firebaseRef=FirebaseDatabase.getInstance().getReference("posts")
    private var postsLists:ArrayList<CoffeePost> = arrayListOf()

    init {
        llistLiveData.value=ArrayList<CoffeePost>()
    }


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