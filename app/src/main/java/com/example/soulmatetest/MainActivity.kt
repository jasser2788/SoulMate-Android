package com.example.soulmatetest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.soulmatetest.models.Catalogue
import com.example.soulmatetest.models.User

import com.example.soulmatetest.utils.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var recylcerCatalogue: RecyclerView
    lateinit var recylcerCatalogueAdapter: CatalogueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*
        recylcerCatalogue = findViewById(R.id.recycleView)

        var CatalogueList : MutableList<MyCatalogue> = ArrayList()
        CatalogueList.add(MyCatalogue(image = R.drawable.fleur, title = "Lee Sin", count = "Category: Decor" ,R.drawable.user))
        CatalogueList.add(MyCatalogue(image = R.drawable.fleur2, title = "sqqs Sin", count = "Category: Decor" ,R.drawable.user))
        CatalogueList.add(MyCatalogue(image = R.drawable.salle, title = "564 Sin", count = "Category: Salle" ,R.drawable.user))
        CatalogueList.add(MyCatalogue(image = R.drawable.musique, title = "bbb Sin", count = "Category: Animation" ,R.drawable.user))
        CatalogueList.add(MyCatalogue(image = R.drawable.fleur, title = "Lee Sin", count = "Category: Decor" ,R.drawable.user))
        CatalogueList.add(MyCatalogue(image = R.drawable.fleur2, title = "sqqs Sin", count = "Category: Decor" ,R.drawable.user))
        CatalogueList.add(MyCatalogue(image = R.drawable.salle, title = "564 Sin", count = "Category: Salle" ,R.drawable.user))
        CatalogueList.add(MyCatalogue(image = R.drawable.musique, title = "bbb Sin", count = "Category: Animation" ,R.drawable.user))




        recylcerCatalogueAdapter = CatalogueAdapter(CatalogueList)
        recylcerCatalogue.adapter = recylcerCatalogueAdapter
        recylcerCatalogue.layoutManager =GridLayoutManager(this, 2)*/


    }

   /* private fun doLogin(){
            val apiInterface = ApiInterface.create()

            apiInterface.getCatalogues().enqueue(object : Callback<MutableList<Catalogue>> {


        }
*/

}