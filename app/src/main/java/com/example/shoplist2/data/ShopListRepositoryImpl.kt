package com.example.shoplist2.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoplist2.domain.ShopList
import com.example.shoplist2.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {

    private var shopList = sortedSetOf<ShopList>({ p0, p1 -> p0.id.compareTo(p1.id) })

    private val listLD = MutableLiveData<List<ShopList>>()

    private var idd = 0

    init {
        for (i in 0 until 10) {
            val it = ShopList(name = "name $i", count = i, enabled = Random.nextBoolean())
            addShopItem(it)
        }
    }

    override fun addShopItem(shopItem: ShopList) {
        if (shopItem.id == ShopList.UNDEFINE_ID) {
            shopItem.id = idd++
        }

        shopList.add(shopItem)
        update()
    }

    override fun deleteShopItem(shopItem: ShopList) {
        shopList.remove(shopItem)
        update()
    }

    override fun editShopItem(shopItem: ShopList) {
        val oldElemnt = getShopItem(shopItem.id)
        shopList.remove(oldElemnt)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopList {
        return shopList.find { it.id == shopItemId }
            ?: throw java.lang.RuntimeException("not found")
    }

    override fun getShopList(): LiveData<List<ShopList>> {
        return listLD
    }

    private fun update() {
        listLD.value = shopList.toList()
    }
}