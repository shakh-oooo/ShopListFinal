package com.example.shoplist2.domain.usecases

import androidx.lifecycle.LiveData
import com.example.shoplist2.domain.ShopList
import com.example.shoplist2.domain.ShopListRepository

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopList(): LiveData<List<ShopList>> {
        return shopListRepository.getShopList()
    }
}