package com.example.shoplist2.domain

data class ShopList(
    val name: String,
    val count: Int,
    val enabled: Boolean,

    var id: Int = UNDEFINE_ID
){
    companion object{
        const val UNDEFINE_ID = -1
    }
}
