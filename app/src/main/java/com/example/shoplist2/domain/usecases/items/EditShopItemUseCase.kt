package com.example.shoplist2.domain.usecases.items

import com.example.shoplist2.domain.ShopList
import com.example.shoplist2.domain.ShopListRepository

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun editShopItem(shopList: ShopList) {
        shopListRepository.editShopItem(shopList)
    }
}