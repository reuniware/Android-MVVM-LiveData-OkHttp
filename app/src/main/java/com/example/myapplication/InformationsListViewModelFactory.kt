package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class InformationsListViewModelFactory(private val context: FirstFragment) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InformationsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return InformationsListViewModel(
                dataSource = MyDataSource(context.resources).getDataSource(context.resources) as MyDataSource
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}