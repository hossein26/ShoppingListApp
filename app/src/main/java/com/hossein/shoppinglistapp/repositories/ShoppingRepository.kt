package com.hossein.shoppinglistapp.repositories

import androidx.lifecycle.LiveData
import com.hossein.shoppinglistapp.data.local.ShoppingItem
import com.hossein.shoppinglistapp.data.remote.responses.ImageResponse
import com.hossein.shoppinglistapp.other.Resource
import retrofit2.Response

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}