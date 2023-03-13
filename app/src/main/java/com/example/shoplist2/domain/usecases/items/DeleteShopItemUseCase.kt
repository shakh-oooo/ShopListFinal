package com.example.shoplist2.domain.usecases.items

import com.example.shoplist2.domain.ShopList
import com.example.shoplist2.domain.ShopListRepository

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun deleteShopItem(shopList: ShopList) {
        shopListRepository.deleteShopItem(shopList)
    }
}