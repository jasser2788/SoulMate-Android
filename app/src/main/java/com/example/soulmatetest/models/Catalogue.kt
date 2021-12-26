package com.example.soulmatetest.models

import com.google.gson.annotations.SerializedName

data class Catalogue (
        @SerializedName("_id") val id : String,
        @SerializedName("category") val category : String,
        @SerializedName("nom") val nom : String,
        @SerializedName("prenom") val prenom : String,
        @SerializedName("picture") val picture : String,
)
