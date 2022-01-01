package com.example.soulmatetest

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.soulmatetest.models.Catalogue
import com.example.soulmatetest.utils.ApiInterface
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.android.synthetic.main.activity_catalogue_user_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class CatalogueUserDetail : AppCompatActivity() {
    private var selectedImageUri: Uri? = null
    lateinit var formater: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogue_user_detail)
        var filename : String
        formater = intent.getStringExtra("picture").toString()

        filename = intent.getStringExtra("picture").toString()
        val path =
            "https://firebasestorage.googleapis.com/v0/b/soulmate-fce7d.appspot.com/o/catalogueImg%2F" + filename + "?alt=media"
        Glide.with(this)
            .load(path)
            .into(imageDU)

        categoryDUtxt.setText(intent.getStringExtra("category"))
        descriptionDUtxt.setText(intent.getStringExtra("description"))

        deleteDU.setOnClickListener(){
            delete()
        }
        updateDU.setOnClickListener(){
            update();
        }
        imageDU.setOnClickListener(){
            openGallery()
        }
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
            imageDU!!.setImageURI(selectedImageUri)
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
            imageDU.setImageURI(selectedImageUri)
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

    private fun delete() {


        val apiInterface = ApiInterface.create()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to delete your post ?")
        builder.setPositiveButton("Yes") { dialogInterface, which ->

            apiInterface.deleteCatalogue(intent.getStringExtra("id").toString()).enqueue(object :
                Callback<Catalogue> {

                override fun onResponse(call: Call<Catalogue>, response: Response<Catalogue>) {
                    val catalogue = response.body()
                    if (catalogue != null) {

                        Toast.makeText(
                            this@CatalogueUserDetail,
                            "deleted successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()


                    } else {
                        Toast.makeText(this@CatalogueUserDetail, "error!", Toast.LENGTH_SHORT)
                            .show()

                    }
                }

                override fun onFailure(call: Call<Catalogue>, t: Throwable) {
                    Toast.makeText(this@CatalogueUserDetail, t.toString(), Toast.LENGTH_LONG).show()

                }
            })
        }
        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss()

        }
        builder.create().show()

    }

    private fun update() {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        val apiInterface = ApiInterface.create()
        val map: HashMap<String, String> = HashMap()

        map["description"] = descriptionDUtxt.text.toString()
        map["category"] = categoryDUtxt.text.toString()
        map["picture"] = formater




        apiInterface.updateCatalogue(intent.getStringExtra("id").toString(),map).enqueue(object :
            Callback<Catalogue> {

            override fun onResponse(call: Call<Catalogue>, response: Response<Catalogue>) {
                val catalogue = response.body()
                if(catalogue !=null)
                {
                    Toast.makeText(this@CatalogueUserDetail,"Updated successfully", Toast.LENGTH_SHORT).show()

                    finish()
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }
                else
                {
                    Toast.makeText(this@CatalogueUserDetail,"error!", Toast.LENGTH_SHORT).show()
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }
            }

            override fun onFailure(call: Call<Catalogue>, t: Throwable) {
                Toast.makeText(this@CatalogueUserDetail,t.toString(), Toast.LENGTH_SHORT).show()
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }
        })
    }

}
