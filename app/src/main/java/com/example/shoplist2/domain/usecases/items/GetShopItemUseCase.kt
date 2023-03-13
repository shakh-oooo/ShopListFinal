package com.example.shoplist2.domain.usecases.items

import com.example.shoplist2.domain.ShopList
import com.example.shoplist2.domain.ShopListRepository

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItem(shopItemId: Int): ShopList {
        return shopListRepository.getShopItem(shopItemId)
    }
}