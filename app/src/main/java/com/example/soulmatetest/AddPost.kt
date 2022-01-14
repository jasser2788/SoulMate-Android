package com.example.soulmatetest

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.soulmatetest.fragments.MyPostFragment
import com.example.soulmatetest.models.Catalogue
import com.example.soulmatetest.models.User
import com.example.soulmatetest.utils.ApiInterface
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.android.synthetic.main.activity_catalogue_detail.*
import kotlinx.android.synthetic.main.activity_change_username.*
import kotlinx.android.synthetic.main.fragment_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPost : AppCompatActivity() {
    private lateinit var mSharedPref: SharedPreferences
    private var selectedImageUri: Uri? = null
    lateinit var formater: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        formater = "No Picture"
        TopHeaderView4.setBackButtonClickListener(){
            onBackPressed()

        }
        btnConfirmAdd.setOnClickListener(){
            if (validate())
              addPost()
}

        imagePost.setOnClickListener(){
            openGallery()
            imagePost.isClickable = false
        }
    }

    override fun onResume() {
        super.onResume()
        imagePost.isClickable = true
        btnConfirmAdd.isClickable = true

    }
    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK)
        {
            selectedImageUri = data?.data!!
            imagePost!!.setImageURI(selectedImageUri)
            uploadImage()
        }
    }

    private fun uploadImage() {
        formater = java.util.UUID.randomUUID().toString()

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading Image ...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val storageReference = FirebaseStorage.getInstance().reference.child("catalogueImg/$formater")
        storageReference.putFile(selectedImageUri!!).
        addOnSuccessListener {
            imagePost.setImageURI(selectedImageUri)
            if(progressDialog.isShowing)
            {
                progressDialog.dismiss()
            }
            Toast.makeText(this,"Image successfuly uploaded", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            if(progressDialog.isShowing)
            {
                progressDialog.dismiss()
            }
            Toast.makeText(this,"upload error", Toast.LENGTH_LONG).show()

        }
    }

    private fun addPost(){
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            val apiInterface = ApiInterface.create()
            /* progBar.visibility = View.VISIBLE

             window.setFlags(
                 WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                 WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
             )*/val map:HashMap<String, String> = HashMap()
        mSharedPref = getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);

        map["user_id"]=mSharedPref.getString(ID, "").toString()
        map["category"]=categorytxt.text.toString()
        map["description"]=descriptiontxt.text.toString()
        map["username"]=mSharedPref.getString(USERNAME, "").toString()
        map["picture"] = formater
        map["ownerpic"] = mSharedPref.getString(PICTURE, "").toString()

            apiInterface.add(map).enqueue(object : Callback<Catalogue> {
                override fun onResponse(call: Call<Catalogue>, response: Response<Catalogue>) {
                    val catalogue = response.body()
                    if (catalogue != null) {
                        Toast.makeText(this@AddPost, "post added!", Toast.LENGTH_SHORT).show()
                        finish()
                        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                    }
                    else Toast.makeText(this@AddPost, "failed!", Toast.LENGTH_SHORT).show()
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                }

                override fun onFailure(call: Call<Catalogue>, t: Throwable) {
                    Toast.makeText(this@AddPost, "Connexion error!", Toast.LENGTH_SHORT).show()
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }


            })


    }

    private fun validate(): Boolean {
        categorytxtLayout.error = null
        descriptiontxtLayout.error = null
        categorytxt.setText(categorytxt.text?.replace("\\s+".toRegex(), " ")?.trim())
        descriptiontxt.setText(descriptiontxt.text?.replace("\\s+".toRegex(), " ")?.trim())

        if (categorytxt.length()< 3){
            categorytxtLayout.error = "Minimum 3 Characters"

            return false
        }
        else categorytxtLayout.error= null

        if (categorytxt.text!!.isEmpty()){
            categorytxtLayout.error = "Must No tBe Empty"

            return false
        }
        else categorytxtLayout.error= null



        if (descriptiontxt.text!!.isEmpty()){
            descriptiontxtLayout.error = "Must No tBe Empty"

            return false
        }
        else descriptiontxtLayout.error= null

        if (descriptiontxt.length()< 10){
            descriptiontxtLayout.error = "Minimum 10 Characters"

            return false
        }
        else descriptiontxtLayout.error= null

        return true
    }

}