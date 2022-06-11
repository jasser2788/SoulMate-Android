package com.example.soulmatetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_catalogue_detail.*
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.soulmatetest.models.Catalogue
import com.example.soulmatetest.models.User
import com.example.soulmatetest.utils.ApiInterface
import kotlinx.android.synthetic.main.activity_catalogue_detail.view.*
import kotlinx.android.synthetic.main.activity_catalogue_user_detail.*
import kotlinx.android.synthetic.main.activity_favorite_detail.*
import kotlinx.android.synthetic.main.single_item_visitor.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap


class CatalogueDetail : AppCompatActivity() {
    private lateinit var mSharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogue_detail)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        TopHeaderView.setBackButtonClickListener(){
            onBackPressed()

        }
        if (intent.getStringExtra("owner").toString() == mSharedPref.getString(USERNAME,"").toString())
        {
            favoriteBtn.visibility = View.GONE
            startChatBtn.visibility = View.GONE
        }

        var filename : String

        filename = intent.getStringExtra("picture").toString()
        val path =
            "https://firebasestorage.googleapis.com/v0/b/soulmateios.appspot.com/o/catalogueImg%2F" + filename + "?alt=media"
        Glide.with(this)
            .load(path)
            .into(imageD)

        ownerDtxt.text = intent.getStringExtra("owner")
        categoryDtxt.text = intent.getStringExtra("category")
        descriptionDtxt.text = intent.getStringExtra("description")

        favoriteBtn.setOnClickListener(){
            favoriteBtn.isClickable = false
            Favorite()
        }
        startChatBtn.setOnClickListener()
        {
            startChatBtn.isClickable = false
            startChat(intent.getStringExtra("user_id").toString())
        }
    }

    override fun onResume() {
        super.onResume()
        favoriteBtn.isClickable = true
        startChatBtn.isClickable = true

    }

    private fun startChat(selectedUser: String) {
        val intent = Intent(this, SingleChat::class.java)
        intent.apply {
            putExtra("selectedUser",selectedUser)
        }
        startActivity(intent)
    }

    private fun Favorite() {

            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            val apiInterface = ApiInterface.create()
            val map: HashMap<String, String> = HashMap()

            map["favorite"] = intent.getStringExtra("_id").toString()





            apiInterface.addFavorite(mSharedPref.getString(ID, "").toString(),map).enqueue(object :
                Callback<User> {

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val user = response.body()

                    if(user !=null)
                    {
                        Toast.makeText(this@CatalogueDetail,"Added to favorite", Toast.LENGTH_SHORT).show()

                        finish()
                        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    }
                    else
                    {
                        Toast.makeText(this@CatalogueDetail,"Already in favorite !", Toast.LENGTH_SHORT).show()
                        favoriteBtn.isClickable = true

                        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@CatalogueDetail,t.toString(), Toast.LENGTH_SHORT).show()
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }
            })
        }


}