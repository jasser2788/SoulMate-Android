package com.example.soulmatetest.utils

import com.example.soulmatetest.models.Catalogue
import com.example.soulmatetest.models.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {
    @POST("users/login")
    fun login(@Body map:HashMap<String, String>): Call<User>


    @POST("users/signup")
    fun signup(@Body map:HashMap<String, String>): Call<User>


    @PATCH("users/update/{id}")
    fun update(@Path("id") id: String?, @Body map : HashMap<String, String> ): Call<User>



    @GET("catalogues")
    fun getCatalogues():Call<MutableList<Catalogue>>

    @POST("catalogues/add")
    fun add(@Body map:HashMap<String, String>): Call<Catalogue>

    @POST("catalogues/userpost")
    fun getUserCatalogue(@Body map:HashMap<String, String>): Call<MutableList<Catalogue>>

    @PATCH("/catalogues/update/{id}")
    fun updateCatalogue(@Path("id") id: String?,@Body map : HashMap<String, String> ): Call<Catalogue>

    @DELETE("/catalogues/delete/{id}")
    fun deleteCatalogue(@Path("id") id: String? ): Call<Catalogue>

    @PATCH("/users/addfavorite/{id}")
    fun addFavorite(@Path("id") id: String?,@Body map : HashMap<String, String> ): Call<User>

    @PATCH("/users/removefavorite/{id}/{idcat}")
    fun removeFavorite(@Path("id") id: String?,@Path("idcat") idcat: String?): Call<User>

    @GET("/catalogues/getfavorite/{id}")
    fun getFavorite(@Path("id") id: String? ): Call<MutableList<Catalogue>>

    companion object {
        var BASE_URL = "http://192.168.1.6:5000/"

       // var BASE_URL = "http://192.168.43.171:5000/"
       //var BASE_URL = "http://192.168.1.9:5000/"


        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}