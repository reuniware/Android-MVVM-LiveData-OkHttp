package com.example.myapplication

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyDataSource(resources: Resources) {
    private val initialInformationList = informationList(resources)
    private val informationsLiveData = MutableLiveData(initialInformationList)

    /* Adds information to liveData and posts value. */
    fun addInformation(information: Information) {
        val currentList = informationsLiveData.value
        if (currentList == null) {
            CoroutineScope(Dispatchers.Main).launch {
                informationsLiveData.postValue(listOf(information))
            }
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, information)
            CoroutineScope(Dispatchers.Main).launch {
                informationsLiveData.postValue(updatedList)
            }
        }
    }

    fun getInformationList(): MutableLiveData<List<Information>> {
        return informationsLiveData
    }

    private var INSTANCE: MyDataSource? = null
    fun getDataSource(resources: Resources): Any {
        return synchronized(MyDataSource::class) {
            val newInstance = INSTANCE ?: MyDataSource(resources)
            INSTANCE = newInstance
            newInstance
        }
    }

}