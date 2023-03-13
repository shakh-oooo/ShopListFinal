package com.example.shoplist2.presentation.rcview

import androidx.recyclerview.widget.DiffUtil
import com.example.shoplist2.domain.ShopList

class ShopListDiffCallback(
    private val oldList: List<ShopList>,
    private val newList: List<ShopList>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return newItem == oldItem
    }
}