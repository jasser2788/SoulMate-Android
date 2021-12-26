package com.example.soulmatetest.models

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("_id") val id : String,
    @SerializedName("username") val username : String,
    @SerializedName("password") val password : String,
    @SerializedName("picture") val picture : String,
)