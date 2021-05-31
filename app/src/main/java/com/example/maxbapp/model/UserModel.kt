package com.example.maxbapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    var name:String? = "",
    var mobile:String? = "",
    var refID:String = ""):Parcelable