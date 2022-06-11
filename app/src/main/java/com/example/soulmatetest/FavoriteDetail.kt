package com.example.soulmatetest

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.soulmatetest.models.User
import com.example.soulmatetest.utils.ApiInterface
import kotlinx.android.synthetic.main.activity_catalogue_detail.*
import kotlinx.android.synthetic.main.activity_catalogue_detail.categoryDtxt
import kotlinx.android.synthetic.main.activity_catalogue_detail.descriptionDtxt
import kotlinx.android.synthetic.main.activity_catalogue_detail.ownerDtxt
import kotlinx.android.synthetic.main.activity_favorite_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class FavoriteDetail : AppCompatActivity() {
    private lateinit var mSharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_detail)

        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);



        var filename : String

        filename = intent.getStringExtra("picture").toString()
        val path =
            "https://firebasestorage.googleapis.com/v0/b/soulmateios.appspot.com/o/catalogueImg%2F" + filename + "?alt=media"
        Glide.with(this)
            .load(path)
            .into(imageF)

        ownerFtxt.text = intent.getStringExtra("owner")
        categoryFtxt.text = intent.getStringExtra("category")
        descriptionFtxt.text = intent.getStringExtra("description")


        removeFbtn.setOnClickListener(){
            removeFbtn.isClickable = false
            remove()
        }

        chatBtn.setOnClickListener(){
            chatBtn.isClickable = false

            startchat(intent.getStringExtra("user_id").toString())
        }
        TopHeaderView2.setBackButtonClickListener(){
            onBackPressed()

        }
    }

    override fun onResume() {
        super.onResume()
        removeFbtn.isClickable = true
        chatBtn.isClickable = true


    }

    private fun startchat(selectedUser:String) {
        val intent = Intent(this, SingleChat::class.java)
       intent.apply {
           putExtra("selectedUser",selectedUser)
       }

        startActivity(intent)
    }

    private fun remove() {

        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        val apiInterface = ApiInterface.create()
        val map: HashMap<String, String> = HashMap()




        apiInterface.removeFavorite(mSharedPref.getString(ID, "").toString(),intent.getStringExtra("_id").toString()).enqueue(object :
            Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {
                val user = response.body()

                if(user !=null)
                {
                    Toast.makeText(this@FavoriteDetail,"Removed from favorite", Toast.LENGTH_SHORT).show()

                    finish()
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }
                else
                {
                    Toast.makeText(this@FavoriteDetail,"error !", Toast.LENGTH_SHORT).show()
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@FavoriteDetail,t.toString(), Toast.LENGTH_SHORT).show()
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }
        })
    }
}