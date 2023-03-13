package com.example.shoplist2.presentation.secondActivity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoplist2.data.ShopListRepositoryImpl
import com.example.shoplist2.domain.ShopList
import com.example.shoplist2.domain.usecases.items.AddShopItemUSeCase
import com.example.shoplist2.domain.usecases.items.EditShopItemUseCase
import com.example.shoplist2.domain.usecases.items.GetShopItemUseCase

class ViewModelSecondActivity : ViewModel() {

    private val repository = ShopListRepositoryImpl


    private val addShopItemUseCase = AddShopItemUSeCase(repository)
    private val getShopItemUSeCase = GetShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)


    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopList = MutableLiveData<ShopList>()
    val shopList: LiveData<ShopList>
        get() = _shopList

    private val _shouldCloseActivity = MutableLiveData<Unit>()
    val shouldCloseActivity: LiveData<Unit>
        get() = _shouldCloseActivity


    fun addItem(name: String?, count: String?) {
        val name = parsingName(name)
        val count = parsingCount(count)
        val validat = validateInput(name, count)
        if (validat) {
            val shopList = ShopList(name, count, true)
            addShopItemUseCase.addShopItem(shopList)
            closeWork()
        }
    }

    fun getItemsId(shopListId: Int) {
        val item = getShopItemUSeCase.getShopItem(shopListId)
        _shopList.value = item
    }

    fun editItems(name: String?, count: String?) {
        val name = parsingName(name)
        val count = parsingCount(count)
        val validat = validateInput(name, count)
        if (validat) {
            _shopList.value?.let {
                val item = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(item)
            }
            closeWork()
        }

    }

    private fun parsingName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parsingCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetInputName() {
        _errorInputName.value = false
    }

    fun resetInputCount() {
        _errorInputCount.value = false
    }

    private fun closeWork() {
        _shouldCloseActivity.value = Unit
    }
}