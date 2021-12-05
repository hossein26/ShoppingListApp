package com.hossein.shoppinglistapp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.hossein.shoppinglistapp.getOrAwaitValue
import com.hossein.shoppinglistapp.launchFragmentInHiltContainer
import com.hossein.shoppinglistapp.ui.AddShoppingItemFragment
import com.hossein.shoppinglistapp.ui.ImagePickFragment
import com.hossein.shoppinglistapp.ui.ShoppingFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ShoppingDaoTest {

    @Inject
    @Named("test_db")
    lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        hiltRule.inject()

        dao = database.shoppingDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun testLaunchFragmentInHiltContainer(){
        launchFragmentInHiltContainer<ShoppingFragment> {  }
        launchFragmentInHiltContainer<ImagePickFragment> {  }
        launchFragmentInHiltContainer<AddShoppingItemFragment> {  }
    }

    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("name", 1, 1f, "url", 1)
        dao.insertShoppingItem(shoppingItem)

        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItem).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("name", 1, 1f, "url", 1)
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItem).doesNotContain(shoppingItem)
    }

    @Test
    fun observeTotalPriceSum() = runBlockingTest {
        val shoppingItem1 = ShoppingItem("name", 2, 3f, "url", id = 1)
        val shoppingItem2 = ShoppingItem("name", 3, 4f, "url", id = 2)
        val shoppingItem3 = ShoppingItem("name", 1, 2f, "url", id = 3)
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val observeTotalPrice = dao.observeTotalPrice().getOrAwaitValue()
        val totalPrice = (shoppingItem1.amount * shoppingItem1.price) +
                (shoppingItem2.amount * shoppingItem2.price) +
                (shoppingItem3.amount * shoppingItem3.price)

        assertThat(observeTotalPrice).isEqualTo(totalPrice)
    }
}















