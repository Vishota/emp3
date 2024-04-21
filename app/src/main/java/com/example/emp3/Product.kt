package com.example.emp3

class Product internal constructor(
    var name: String,
    var price: Int,
    var image: Int,
    var box: Boolean
) {
    fun getPriceString(): String {
        return "$price"
    }
}
