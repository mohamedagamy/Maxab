package com.example.maxbapp.repo

import com.example.maxbapp.model.UserModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class AppRepository(val firestore: FirebaseFirestore) {
    suspend fun getUserList(): QuerySnapshot? {
        return try {
            val data = firestore.collection("users")
                .get().await()
             data
        }catch (ex:Exception){
             null
        }
    }

    suspend fun addNewUser(userModel: UserModel):Boolean {
        return try {
            val data = HashMap<String, String>()
            data.put("name",userModel.name.toString())
            data.put("mobile",userModel.mobile.toString())
            val newUserRef = firestore.collection("users").document()
            newUserRef.set(data).await()
            true
        }catch (ex:Exception){
            false
        }
    }

    suspend fun deleteUser(userModel: UserModel):Boolean {
        return try {
            firestore.collection("users")
                .document(userModel.refID)
                .delete().await()
            true
        }catch (ex:Exception){
            false
        }
    }


}