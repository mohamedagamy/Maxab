package com.example.maxbapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.maxbapp.model.UserModel
import com.example.maxbapp.repo.AppRepository

class ContactViewModel (val appRepository: AppRepository): ViewModel() {
    var users:MutableList<UserModel>? = mutableListOf()

    suspend fun getUserList(): List<UserModel>? {
        if(users.isNullOrEmpty()) {
            appRepository.getUserList()?.documents?.forEach {
                users?.add(UserModel(it.get("name").toString(),it.get("mobile").toString(),it.id))
            }

        }
        return users
    }

    suspend fun addNewUser(userModel: UserModel): Boolean {
        users?.add(userModel)
        return appRepository.addNewUser(userModel)
    }

    suspend fun deleteUser(userModel: UserModel):Boolean {
        users?.remove(userModel)
        return appRepository.deleteUser(userModel)
    }

}