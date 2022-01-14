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

        holder.itemView.isClickable = true

        var filename : String
        val current = catlogueList[position]
        holder.itemView.apply {
            filename = current.picture
            val path =
                "https://firebasestorage.googleapis.com/v0/b/soulmate-fce7d.appspot.com/o/catalogueImg%2F" + filename + "?alt=media"
            Glide.with(this)
                .load(path)
                .into(imageCatalogue)

            //holder.picture.setImageResource(current.picture)
            holder.category.text = "Offer: "+current.category
            holder.name.text = current.nom
            //holder.ownerPic.setImageResource(current.ownerPic)
            // val manager = (holder.itemView.context as FragmentActivity).supportFragmentManager
            holder.itemView.setOnClickListener(){
                val intent = Intent(holder.itemView.context, CatalogueDetail::class.java)
                intent.apply {
                    holder.itemView.isClickable = false

                    putExtra("_id",current.id)
                    putExtra("user_id",current.user_id)
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