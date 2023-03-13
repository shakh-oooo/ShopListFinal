package com.example.shoplist2.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun addShopItem(shopItem: ShopList)
    fun deleteShopItem(shopItem: ShopList)
    fun editShopItem(shopItem: ShopList)
    fun getShopItem(shopItemId : Int):ShopList

    fun getShopList():LiveData<List<ShopList>>


}