package com.example.soulmatetest.models

import com.google.gson.annotations.SerializedName

data class Catalogue (
        @SerializedName("_id") val id : String,
        @SerializedName("user_id") val user_id : String,
        @SerializedName("category") val category : String,
        @SerializedName("description") val description : String,
        @SerializedName("username") val nom : String,
        @SerializedName("picture") val picture : String,
)
