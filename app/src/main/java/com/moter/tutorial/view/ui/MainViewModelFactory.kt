package com.moter.tutorial.view.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moter.tutorial.network.INewsServices
import java.lang.IllegalArgumentException

class MainViewModelFactory constructor(private val service: INewsServices) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val mainRepository = MainRepository(service)
            MainViewModel(mainRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }

}