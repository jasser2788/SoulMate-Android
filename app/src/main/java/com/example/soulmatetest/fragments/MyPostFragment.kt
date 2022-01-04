package com.example.soulmatetest.fragments

import android.content.Intent
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
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.example.soulmatetest.*
import com.example.soulmatetest.models.Catalogue
import com.example.soulmatetest.utils.ApiInterface
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_my_post.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.searchtoolbar.*
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
        btnAdd.isEnabled = true

        loadData()
        search_text.setText("")
        nopost.text = ""

    }

    fun loadData(){
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        val user_id = mSharedPref.getString(ID, "").toString()

        val apiInterface = ApiInterface.create()
        val map:HashMap<String, String> = HashMap()

        map["user_id"]=user_id
        apiInterface.getUserCatalogue(map).enqueue(object : Callback<MutableList<Catalogue>> {
            override fun onResponse(call: Call<MutableList<Catalogue>>, response: Response<MutableList<Catalogue>>
            ) {
                    if(isAdded) {
                        recylcerCatalogueAdapter = CatalogueUserAdapter(response.body()!!)
                        recycleUserPost.adapter = recylcerCatalogueAdapter
                        recycleUserPost.layoutManager = GridLayoutManager(context, 2)


                if (recylcerCatalogueAdapter.itemCount == 0) {
                    nopost.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50.toFloat());

                    nopost.text = "No posts yet !"
                }
                else{
                    nopost.text = ""

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        btnAdd.setOnClickListener(){
            btnAdd.isEnabled = false
            val intent = Intent (context, AddPost::class.java)
            context?.startActivity(intent)

        }

       /* */
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
                    nopost.text = ""
                }

                if (search_text.text.toString() != "") {
                    mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
                    val user_id = mSharedPref.getString(ID, "").toString()

                    val apiInterface = ApiInterface.create()
                    val map: HashMap<String, String> = HashMap()
                    map["user_id"]=user_id
                    map["category"] = search_text.text.toString()

                    apiInterface.searchuserpost(map).enqueue(object : Callback<MutableList<Catalogue>> {
                        override fun onResponse(
                            call: Call<MutableList<Catalogue>>,
                            response: Response<MutableList<Catalogue>>
                        ) {
                            if(isAdded) {

                                recylcerCatalogueAdapter = CatalogueUserAdapter(response.body()!!)
                                recycleUserPost.adapter = recylcerCatalogueAdapter
                                recycleUserPost.layoutManager = GridLayoutManager(context, 2)
                                if (recylcerCatalogueAdapter.itemCount == 0) {
                                    nopost.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15.toFloat());

                                    nopost.text = search_text.text.toString() +" Does not exist!"
                                }
                                else {
                                    nopost.text = ""

                                    nopost.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50.toFloat());

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
                    val user_id = mSharedPref.getString(ID, "").toString()

                    val apiInterface = ApiInterface.create()
                    val map:HashMap<String, String> = HashMap()

                    map["user_id"]=user_id
                    apiInterface.getUserCatalogue(map).enqueue(object : Callback<MutableList<Catalogue>> {
                        override fun onResponse(call: Call<MutableList<Catalogue>>, response: Response<MutableList<Catalogue>>
                        ) {
                            if(isAdded) {

                                recylcerCatalogueAdapter = CatalogueUserAdapter(response.body()!!)
                                recycleUserPost.adapter = recylcerCatalogueAdapter
                                recycleUserPost.layoutManager = GridLayoutManager(context, 2)

                            if (recylcerCatalogueAdapter.itemCount == 0) {
                                nopost.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50.toFloat());

                                nopost.text = "No posts yet !"
                            }
                            else{
                                nopost.text = ""

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
            }
        })
    }

}