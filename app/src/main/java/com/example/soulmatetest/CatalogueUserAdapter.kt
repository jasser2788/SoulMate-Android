package com.example.soulmatetest

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.soulmatetest.models.Catalogue
import kotlinx.android.synthetic.main.single_item_visitor.view.*
import kotlinx.android.synthetic.main.single_item_visitor.view.imageCatalogue
import kotlinx.android.synthetic.main.single_post.view.*

class CatalogueUserAdapter (val catlogueList: MutableList<Catalogue> ): RecyclerView.Adapter<CatalogueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogueViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item_visitor, parent, false)

        return CatalogueViewHolder(view)    }

    override fun onBindViewHolder(holder: CatalogueViewHolder, position: Int) {


        var filename : String
        var filename2 : String

        val current = catlogueList[position]
        holder.itemView.apply {
            filename = current.picture
            val path =
                "https://firebasestorage.googleapis.com/v0/b/soulmate-fce7d.appspot.com/o/catalogueImg%2F" + filename + "?alt=media"
            Glide.with(this)
                .load(path)
                .into(imageCatalogue)
          //  Toast.makeText(holder.itemView.context, current.ownerpic , Toast.LENGTH_SHORT).show()

  /*  filename2 = current.ownerpic
    val path2 =
        "https://firebasestorage.googleapis.com/v0/b/soulmate-fce7d.appspot.com/o/images%2F" + filename2 + "?alt=media"
    Glide.with(this)
        .load(path2)
        .into(ownerPic)*/

            //holder.picture.setImageResource(current.picture)
            holder.category.text = "Category: "+current.category
            holder.name.text = current.nom
            //holder.ownerPic.setImageResource(current.ownerPic)
            // val manager = (holder.itemView.context as FragmentActivity).supportFragmentManager
            holder.itemView.setOnClickListener(){
                val intent = Intent(holder.itemView.context, CatalogueUserDetail::class.java)
                intent.apply {
                    putExtra("id",current.id)
                    putExtra("picture",current.picture)
                    putExtra("owner",current.nom)
                    putExtra("category",current.category)
                    putExtra("description", current.description)


                }
                holder.itemView.context.startActivity(intent)   }

        }





    }

    override fun getItemCount()= catlogueList.size
}