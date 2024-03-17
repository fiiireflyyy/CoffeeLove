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
    private val database=Firebase.database.reference
    //Ссылка на все посты
    private val firebaseRefPosts=FirebaseDatabase.getInstance().getReference("posts")

    //Ссылка на пользователя
    private val userRef=database.child("Users").child("TestUser")

    private var countMyPost:Int=10000


    //Массив моих постов
    private val myPost=ArrayList<CoffeePost>()

    init {
        llistLiveData.value=ArrayList<CoffeePost>()
    }


    //Установка слушателя для фоновой подкачки
    fun backGroundLoadAllPosts(){
            firebaseRefPosts.addValueEventListener(object :ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    llistLiveData.value?.clear()
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



    //Функция отправления поста в базу данных
    fun createPost(
        id: Long,
        userNickName:String,
        countLike:Long,
        recipeName:String,
        recipeDescription:String){

        val coffeePost=CoffeePost(id,userNickName,countLike, recipeName,recipeDescription)
        myPost.add(coffeePost)
        Firebase.database.getReference("Users/$userNickName/MyPost/post${myPost.size}").setValue(id)
        Firebase.database.getReference("Users/$userNickName/countPost").setValue(myPost.size.toString())
//        database.child("Users").child(userNickName).child("MyPost").child("post${myPost.size}").setValue(id)
        database.child("posts").child(id.toString()).setValue(coffeePost)
    }


    fun getCountMyPost(){
        userRef.child("countPost").get().addOnSuccessListener {
            countMyPost=it.getValue(Int::class.java)!!
        }
    }


//Функция получения моих постов из базы
    fun getMyPostFromBase(){
        var idValue:Long=10
        var idMyPostRef:DatabaseReference
        myPost.clear()

        for (i in  1..countMyPost){
            Log.d("FENIXXX","post$i")
            idMyPostRef=userRef.child("MyPost").child("post$i")

            idMyPostRef.get().addOnSuccessListener {snapshot->
                idValue= snapshot.getValue(Long::class.java)!!
                database.child("posts").child(idValue.toString()).get().addOnSuccessListener {
                    myPost.add(it.getValue(CoffeePost::class.java)!!)
                }
                Log.d("FENIXXX",idValue.toString())
                Log.d("FENIXXX",myPost.isEmpty().toString())
            }

        }

    }



    //Функция выдачи списка постов
    fun getMyPostList(): ArrayList<CoffeePost> {
        return myPost
    }

    fun getGenerateId(): Long {
        val sdf=SimpleDateFormat("yyyMMddHHmmss")
        val id=sdf.format(Date())
        Log.d("FENIXXX",id)
        return id.toLong()
    }




}