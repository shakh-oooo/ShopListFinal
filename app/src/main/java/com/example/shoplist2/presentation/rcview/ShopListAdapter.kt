package com.example.shoplist2.presentation.rcview

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoplist2.R
import com.example.shoplist2.domain.ShopList

class ShopListAdapter :
    androidx.recyclerview.widget.ListAdapter<ShopList, ShopItemViewHolder>(ShopItemCallback()) {

    /*var shopListFromAdapter = listOf<ShopList>()
        set(value) {
            val callback = ShopListDiffCallback(shopListFromAdapter, value)
            val calculate = DiffUtil.calculateDiff(callback)
            calculate.dispatchUpdatesTo(this)
            field = value

        }
*/
    var setLongClickListener: ((ShopList) -> Unit)? = null
    var setOnClickListener: ((ShopList) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw java.lang.RuntimeException("Unknown view type (не известный тип) $viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)

        //этот класс нужен для того чтоб сдувать класс из view  столько раз сколько понадбится
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shoppItem = getItem(position)
        holder.view.setOnLongClickListener {
            setLongClickListener?.invoke(shoppItem)
            true
        }
        holder.view.setOnClickListener {
            setOnClickListener?.invoke(shoppItem)
        }
        holder.view


        holder.tvName.text = shoppItem.name
        holder.tvCount.text = shoppItem.count.toString()

        //этот класс присваевает айтемы к нужнам элемантам
        //обрабатывает нажатие и т.д.
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled) VIEW_TYPE_ENABLED
        else VIEW_TYPE_DISABLED
        //класс нужен для того чтоб менять макеты

    }
/*
    override fun getItemCount(): Int {
        return shopListFromAdapter.size
    }*/

    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 2

        const val MAX_SIZE_POOL = 13


    }

}