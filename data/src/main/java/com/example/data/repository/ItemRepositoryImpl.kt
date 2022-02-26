package com.example.data.repository

import android.util.Log
import com.example.data.network.ApiService
import com.example.data.network.model.ApiResponse
import com.example.data.repository.model.GetItemResponse
import com.example.data.repository.model.GetItemsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ItemRepository {

    private val refreshIntervalMsShort: Long = 1000
    private val refreshIntervalMsLong: Long = 600000

    override fun getItem(queryPath: String): Flow<GetItemResponse> {
        return flow {
            val source = apiService.getItem(queryPath)
            var finalResponse = GetItemResponse("initial", null)
            source.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            finalResponse = GetItemResponse("successful", it.response.content)
                        }
                    }
                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.e("Failure", "Message " + t.message)
                    finalResponse = GetItemResponse(if (t.message == "Unable to resolve host \"content.guardianapis.com\": No address associated with hostname") "no internet" else "error", null)
                }
            })
            while (true) {
                emit(finalResponse)
                if (finalResponse.status == "initial") {
                    delay(refreshIntervalMsShort)
                } else {
                    delay(refreshIntervalMsLong)
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getItems(query: String?, section: String, tag: String?): Flow<GetItemsResponse> {
        return flow {
            val source = apiService.getItems(query, section, tag)
            var finalResponse = GetItemsResponse("initial", listOf())
            source.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            it.response.results?.let { it1 ->
                                finalResponse = GetItemsResponse(if (it1.isEmpty()) "empty" else "filled", it1)
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.e("Failure", "Message " + t.message)
                    finalResponse = GetItemsResponse(if (t.message == "Unable to resolve host \"content.guardianapis.com\": No address associated with hostname") "no internet" else "error", listOf())
                }
            })
            while (true) {
                emit(finalResponse)
                if (finalResponse.status == "initial") {
                    delay(refreshIntervalMsShort)
                } else {
                    delay(refreshIntervalMsLong)
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}