package com.example.shoplist2.presentation.rcview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist2.R

class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val tvName = view.findViewById<TextView>(R.id.tv_name)
    val tvCount = view.findViewById<TextView>(R.id.tv_count)

    //этот класс нужен для того чтоб инициализировать айтэмы в наших проектах,
    //чтоб дальнейшем получить адреса потом их не искать каждый раз
}