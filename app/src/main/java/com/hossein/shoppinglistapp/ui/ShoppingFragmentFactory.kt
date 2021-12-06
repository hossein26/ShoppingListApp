package com.hossein.shoppinglistapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.hossein.shoppinglistapp.adapters.ImageAdapter
import javax.inject.Inject

class ShoppingFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImagePickFragment::class.java.name -> ImagePickFragment(imageAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}