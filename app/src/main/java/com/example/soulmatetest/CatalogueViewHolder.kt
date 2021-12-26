package com.example.soulmatetest

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class CatalogueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val picture : ImageView = itemView.findViewById(R.id.imageCatalogue)
    val category : TextView = itemView.findViewById(R.id.category)
    val name : TextView = itemView.findViewById(R.id.name)
    val ownerPic : ImageView = itemView.findViewById(R.id.ownerPic)

}