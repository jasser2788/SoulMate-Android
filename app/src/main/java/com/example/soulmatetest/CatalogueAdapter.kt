package com.example.soulmatetest

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.soulmatetest.models.Catalogue
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.single_item_visitor.view.*

class CatalogueAdapter(val catlogueList: MutableList<Catalogue> ): RecyclerView.Adapter<CatalogueViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogueViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item_visitor, parent, false)

        return CatalogueViewHolder(view)    }

    override fun onBindViewHolder(holder: CatalogueViewHolder, position: Int) {

        var filename : String
        holder.itemView.apply {
            val current = catlogueList[position]
            filename = current.picture
            val path =
                "https://firebasestorage.googleapis.com/v0/b/soulmate-fce7d.appspot.com/o/catalogueImg%2F" + filename + "?alt=media"
            Glide.with(this)
                .load(path)
                .into(imageCatalogue)

            //holder.picture.setImageResource(current.picture)
            holder.category.text = "Category: "+current.category
            holder.name.text = current.nom + " " + current.prenom
            //holder.ownerPic.setImageResource(current.ownerPic)
            // val manager = (holder.itemView.context as FragmentActivity).supportFragmentManager
        }
        holder.ownerPic.setOnClickListener {
            /*  val transaction =manager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, vendorProfile()).addToBackStack("")
                .commit()*/
          val intent = Intent(holder.itemView.context, VendorProfileActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }





    }

    override fun getItemCount()= catlogueList.size
}