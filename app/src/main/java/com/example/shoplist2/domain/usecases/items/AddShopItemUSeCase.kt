package com.example.shoplist2.domain.usecases.items

import com.example.shoplist2.domain.ShopList
import com.example.shoplist2.domain.ShopListRepository


class AddShopItemUSeCase(private val shopListRepository: ShopListRepository) {
    fun addShopItem(shopList: ShopList) {
        shopListRepository.addShopItem(shopList)
    }
}