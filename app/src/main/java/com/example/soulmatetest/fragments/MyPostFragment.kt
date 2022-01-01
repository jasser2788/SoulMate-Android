package com.example.soulmatetest.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.example.soulmatetest.*
import com.example.soulmatetest.models.Catalogue
import com.example.soulmatetest.utils.ApiInterface
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_my_post.*
import kotlinx.android.synthetic.main.fragment_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyPostFragment : Fragment() {
    private lateinit var mSharedPref: SharedPreferences

    lateinit var recylcerCatalogueAdapter: CatalogueUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_post, container, false)
    }
    override fun onResume() {
        super.onResume()
        loadData()

    }

    fun loadData(){
        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        val user_id = mSharedPref.getString(ID, "").toString()

        val apiInterface = ApiInterface.create()
        val map:HashMap<String, String> = HashMap()

        map["user_id"]=user_id
        apiInterface.getUserCatalogue(map).enqueue(object : Callback<MutableList<Catalogue>> {
            override fun onResponse(call: Call<MutableList<Catalogue>>, response: Response<MutableList<Catalogue>>
            ) {

                recylcerCatalogueAdapter = CatalogueUserAdapter(response.body()!!)
                recycleUserPost.adapter = recylcerCatalogueAdapter
                recycleUserPost.layoutManager = GridLayoutManager(context, 2)
                if (recylcerCatalogueAdapter.itemCount == 0) {
                    nopost.text = "No posts yet !"
                }
                else{
                    nopost.text = ""

                }
            }

            override fun onFailure(call: Call<MutableList<Catalogue>>, t: Throwable) {
                Toast.makeText(activity, "Connexion error!", Toast.LENGTH_SHORT).show()
            }


        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        btnAdd.setOnClickListener(){
            val intent = Intent (context, AddPost::class.java)
            context?.startActivity(intent)

        }


    }

}