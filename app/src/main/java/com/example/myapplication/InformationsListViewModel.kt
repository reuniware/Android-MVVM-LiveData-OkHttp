package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

class InformationsListViewModel(val dataSource: MyDataSource) : ViewModel() {

    val informationsLiveData = dataSource.getInformationList()

    fun insertInformation(informationId: String?, informationText: String?) {
        if (informationId == null || informationText == null) {
            return
        }

        val newInformation = Information(informationId, informationText)
        dataSource.addInformation(newInformation)
    }

    fun triggerNetworkRequests() {

        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://dummyjson.com/quotes") // https://dummyjson.com/quotes
                .build()
            client.newCall(request).execute().use {
                if (!it.isSuccessful) insertInformation("000", "Unexpected code $it")
                else {
                    val body = it.body!!.string()

                    val gson = Gson()
                    val quotes : Quotes = gson.fromJson(body, Quotes::class.java)

                    quotes.quotes.forEach { q ->
                        Log.d("test", "inserting quote : " + q.id + " " + q.quote)
                        insertInformation(q.id.toString(), q.quote)
                        Thread.sleep(100)
                    }

                }
            }
        }

    }
}


class Quotes(
    val quotes: List<Quote>
)

class Quote(
    val id: Long,
    val quote: String,
    val author: String
)