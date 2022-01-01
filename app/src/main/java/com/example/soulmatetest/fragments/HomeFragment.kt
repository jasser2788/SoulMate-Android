package com.example.soulmatetest.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.soulmatetest.CatalogueAdapter
import com.example.soulmatetest.R
import com.example.soulmatetest.models.Catalogue
import com.example.soulmatetest.utils.ApiInterface
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {


    lateinit var recylcerCatalogue: RecyclerView
    lateinit var recylcerCatalogueAdapter: CatalogueAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*recylcerCatalogue = findViewById(R.id.recycleView)
        var CatalogueList : MutableList<Catalogue> = ArrayList()
        CatalogueList.add(Catalogue(image = R.drawable.fleur, title = "Lee Sin", count = "Category: Decor" ,R.drawable.user))
        CatalogueList.add(Catalogue(image = R.drawable.fleur2, title = "sqqs Sin", count = "Category: Decor" ,R.drawable.user))
        CatalogueList.add(Catalogue(image = R.drawable.salle, title = "564 Sin", count = "Category: Salle" ,R.drawable.user))
        CatalogueList.add(Catalogue(image = R.drawable.musique, title = "bbb Sin", count = "Category: Animation" ,R.drawable.user))
        CatalogueList.add(Catalogue(image = R.drawable.fleur, title = "Lee Sin", count = "Category: Decor" ,R.drawable.user))
        CatalogueList.add(Catalogue(image = R.drawable.fleur2, title = "sqqs Sin", count = "Category: Decor" ,R.drawable.user))
        CatalogueList.add(Catalogue(image = R.drawable.salle, title = "564 Sin", count = "Category: Salle" ,R.drawable.user))
        CatalogueList.add(Catalogue(image = R.drawable.musique, title = "bbb Sin", count = "Category: Animation" ,R.drawable.user))
   recylcerCatalogueAdapter = CatalogueAdapter(CatalogueList)
        myrecycleView.adapter = recylcerCatalogueAdapter
        myrecycleView.layoutManager = GridLayoutManager(context, 2)*/
        val apiInterface = ApiInterface.create()

        apiInterface.getCatalogues().enqueue(object : Callback<MutableList<Catalogue>> {
            override fun onResponse(call: Call<MutableList<Catalogue>>, response: Response<MutableList<Catalogue>>
            ) {

                recylcerCatalogueAdapter = CatalogueAdapter(response.body()!!)
                myrecycleView.adapter = recylcerCatalogueAdapter
                myrecycleView.layoutManager = GridLayoutManager(context, 2)
            }

            override fun onFailure(call: Call<MutableList<Catalogue>>, t: Throwable) {
                Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show()
            }


        })

    }

//    private fun showCatalogue(catalogues: MutableList<Catalogue>?) {
//
//        recylcerCatalogueAdapter = CatalogueAdapter(catalogues!!)
//        myrecycleView.adapter = recylcerCatalogueAdapter
//        myrecycleView.layoutManager = GridLayoutManager(context, 2)
//    }
  /*  private fun doLogin() {
        val apiInterface = ApiInterface.create()

        apiInterface.getCatalogues().enqueue(object : Callback<MutableList<Catalogue>> {
            override fun onResponse(call: Call<MutableList<Catalogue>>, response: Response<MutableList<Catalogue>>
            ) {
                showCatalogue(response.body())
            }

            override fun onFailure(call: Call<MutableList<Catalogue>>, t: Throwable) {
                Toast.makeText(activity, "Connexion error!", Toast.LENGTH_SHORT).show()
            }


        })


    }*/


}