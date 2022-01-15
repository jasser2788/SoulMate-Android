package com.example.soulmatetest.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.soulmatetest.*
import com.example.soulmatetest.models.Catalogue
import com.example.soulmatetest.utils.ApiInterface
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_my_post.*
import kotlinx.android.synthetic.main.searchtoolbar.*
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
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            );
        nofavorite.text = ""

        search_text.setText("")
            loadData()
        swipeFavorite.isRefreshing = false


    }

    private fun loadData() {
        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        val apiInterface = ApiInterface.create()

        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


        apiInterface.getFavorite(mSharedPref.getString(ID, "").toString()).enqueue(object : Callback<MutableList<Catalogue>> {
            override fun onResponse(call: Call<MutableList<Catalogue>>, response: Response<MutableList<Catalogue>>
            ) {
                    if(isAdded) {

                        recylcerCatalogueAdapter = FavoriteAdapter(response.body()!!)
                        recyclefavorite.adapter = recylcerCatalogueAdapter
                        recyclefavorite.layoutManager = GridLayoutManager(context, 2)

                        if (recylcerCatalogueAdapter.itemCount == 0) {
                            nofavorite.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50.toFloat());

                            nofavorite.text = "No Favorite !"
                        }
                        else {
                            nofavorite.text = ""

                            nofavorite.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50.toFloat());

                        }
                    }
                    activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);




                }

            override fun onFailure(call: Call<MutableList<Catalogue>>, t: Throwable) {
                Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show()
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }


        })    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        swipeFavorite.setColorSchemeResources(R.color.mycolor)
        swipeFavorite.setOnRefreshListener { onResume() }

        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        search_text.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

                if (search_text.text.toString() != "") {
                    nofavorite.text = ""
                }
                if (search_text.text.toString() != "") {
                    mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);

                    val apiInterface = ApiInterface.create()
                    val map: HashMap<String, String> = HashMap()
                    map["id"]=mSharedPref.getString(ID, "").toString()
                    map["category"] = search_text.text.toString()
                    apiInterface.searchfavorite(map).enqueue(object : Callback<MutableList<Catalogue>> {
                        override fun onResponse(
                            call: Call<MutableList<Catalogue>>,
                            response: Response<MutableList<Catalogue>>
                        ) {
                             if(isAdded) {
                                 recylcerCatalogueAdapter = FavoriteAdapter(response.body()!!)
                                 recyclefavorite.adapter = recylcerCatalogueAdapter
                                 recyclefavorite.layoutManager = GridLayoutManager(context, 2)
                                 if (recylcerCatalogueAdapter.itemCount == 0) {
                                     nofavorite.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15.toFloat());

                                     nofavorite.text = search_text.text.toString() + " Does Not Exist !"
                                 }
                                 else {
                                     nofavorite.text = ""

                                     nofavorite.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50.toFloat());

                                 }
                             }
                                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);




                        }

                        override fun onFailure(call: Call<MutableList<Catalogue>>, t: Throwable) {
                            Toast.makeText(activity, "Connexion error!", Toast.LENGTH_SHORT).show()
                            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        }


                    })
                }
                if (search_text.text.toString() == "") {
                    mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);

                    val apiInterface = ApiInterface.create()

                    apiInterface.getFavorite(mSharedPref.getString(ID, "").toString()).enqueue(object : Callback<MutableList<Catalogue>> {
                        override fun onResponse(call: Call<MutableList<Catalogue>>, response: Response<MutableList<Catalogue>>
                        ) {
                                if(isAdded) {

                                    recylcerCatalogueAdapter = FavoriteAdapter(response.body()!!)
                                    recyclefavorite.adapter = recylcerCatalogueAdapter
                                    recyclefavorite.layoutManager = GridLayoutManager(context, 2)



                                    if (recylcerCatalogueAdapter.itemCount == 0) {
                                        nofavorite.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50.toFloat());

                                        nofavorite.text = "No Favorite !"
                                    } else
                                    {
                                        nofavorite.text = ""

                                    }
                                }
                                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                        }

                        override fun onFailure(call: Call<MutableList<Catalogue>>, t: Throwable) {
                            Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show()
                            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        }


                    })

                }
            }
        })
    }

}