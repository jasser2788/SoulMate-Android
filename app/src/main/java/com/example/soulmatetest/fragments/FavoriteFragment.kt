package com.example.soulmatetest.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.soulmatetest.*
import com.example.soulmatetest.models.Catalogue
import com.example.soulmatetest.utils.ApiInterface
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_my_post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FavoriteFragment : Fragment() {


    lateinit var recylcerCatalogueAdapter: FavoriteAdapter

    private lateinit var mSharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }
    override fun onResume() {
        super.onResume()
        loadData()

    }

    private fun loadData() {
        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        val apiInterface = ApiInterface.create()



        apiInterface.getFavorite(mSharedPref.getString(ID, "").toString()).enqueue(object : Callback<MutableList<Catalogue>> {
            override fun onResponse(call: Call<MutableList<Catalogue>>, response: Response<MutableList<Catalogue>>
            ) {

                recylcerCatalogueAdapter = FavoriteAdapter(response.body()!!)
                recyclefavorite.adapter = recylcerCatalogueAdapter
                recyclefavorite.layoutManager = GridLayoutManager(context, 2)
                if (recylcerCatalogueAdapter.itemCount == 0) {
                    nofavorite.text = "No Favorite !"
                }
                else{
                    nofavorite.text = ""

                }
            }

            override fun onFailure(call: Call<MutableList<Catalogue>>, t: Throwable) {
                Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show()
            }


        })    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }


}