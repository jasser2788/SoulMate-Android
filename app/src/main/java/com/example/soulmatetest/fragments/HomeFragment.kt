package com.example.soulmatetest.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
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
import kotlinx.android.synthetic.main.searchtoolbar.*
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

    override fun onResume() {
        super.onResume()
        search_text.setText("")

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


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
                    val apiInterface = ApiInterface.create()
                    val map: HashMap<String, String> = HashMap()

                    map["category"] = search_text.text.toString()
                    apiInterface.search(map).enqueue(object : Callback<MutableList<Catalogue>> {
                        override fun onResponse(
                            call: Call<MutableList<Catalogue>>,
                            response: Response<MutableList<Catalogue>>
                        ) {
                            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            recylcerCatalogueAdapter = CatalogueAdapter(response.body()!!)
                            myrecycleView.adapter = recylcerCatalogueAdapter
                            myrecycleView.layoutManager = GridLayoutManager(context, 2)


                        }

                        override fun onFailure(call: Call<MutableList<Catalogue>>, t: Throwable) {
                            Toast.makeText(activity, "Connexion error!", Toast.LENGTH_SHORT).show()
                            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        }


                    })
                }
                    if (search_text.text.toString() == "") {
                        val apiInterface = ApiInterface.create()

                        apiInterface.getCatalogues().enqueue(object : Callback<MutableList<Catalogue>> {

                            override fun onResponse(
                                call: Call<MutableList<Catalogue>>, response: Response<MutableList<Catalogue>>
                            ) {

                                recylcerCatalogueAdapter = CatalogueAdapter(response.body()!!)
                                myrecycleView.adapter = recylcerCatalogueAdapter
                                myrecycleView.layoutManager = GridLayoutManager(context, 2)
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

//

        val apiInterface = ApiInterface.create()

        apiInterface.getCatalogues().enqueue(object : Callback<MutableList<Catalogue>> {

            override fun onResponse(
                call: Call<MutableList<Catalogue>>, response: Response<MutableList<Catalogue>>
            ) {

                recylcerCatalogueAdapter = CatalogueAdapter(response.body()!!)
                myrecycleView.adapter = recylcerCatalogueAdapter
                myrecycleView.layoutManager = GridLayoutManager(context, 2)
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }

            override fun onFailure(call: Call<MutableList<Catalogue>>, t: Throwable) {

                Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show()
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

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