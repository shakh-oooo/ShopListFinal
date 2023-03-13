package com.example.shoplist2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.shoplist2.data.ShopListRepositoryImpl
import com.example.shoplist2.domain.ShopList
import com.example.shoplist2.domain.usecases.GetShopListUseCase
import com.example.shoplist2.domain.usecases.items.DeleteShopItemUseCase
import com.example.shoplist2.domain.usecases.items.EditShopItemUseCase

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)


    val shopList = getShopListUseCase.getShopList()


    fun delete(shopList: ShopList) {
        deleteShopItemUseCase.deleteShopItem(shopList)
        //удаляет данные функция delete принимает лист (его данные) потом передает на реалезацию (на удаление)
    }

    fun editTrueFalse(shopList: ShopList) {
        val new = shopList.copy(enabled = !shopList.enabled)
        editShopItemUseCase.editShopItem(new)
        //изменятет true false перемменя New равна копии отрицательную Enabled тоист если нам приходить true он всегда будет false
    }


}