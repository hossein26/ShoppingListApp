package com.hossein.shoppinglistapp.repositories

import androidx.lifecycle.LiveData
import com.hossein.shoppinglistapp.data.local.ShoppingDao
import com.hossein.shoppinglistapp.data.local.ShoppingItem
import com.hossein.shoppinglistapp.data.remote.PixabayAPI
import com.hossein.shoppinglistapp.data.remote.responses.ImageResponse
import com.hossein.shoppinglistapp.other.Resource
import retrofit2.Response
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
) : ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            }else{
                Resource.error("An unknown error occurred, null", null)
            }
        }catch (e: Exception){
            Resource.error("Couldn't reach the server. Check Your internet connection", null)
        }
    }
}










