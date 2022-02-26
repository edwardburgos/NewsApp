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
            var finalResponse = GetItemResponse("initial", null)
            try {
                var result = apiService.getItem(queryPath)
                result.response.content?.let {
                    finalResponse = GetItemResponse("successful", it)
                }
            } catch (e: Exception) {
                e.message?.let {
                    finalResponse = GetItemResponse(if (it.contains("Unable to resolve host \"content.guardianapis.com\": No address associated with hostname")) "no internet" else "error", null)
                }
            }
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
            var finalResponse = GetItemsResponse("initial", listOf())
            try {
                var result = apiService.getItems(query, section, tag)
                result.response.results?.let {
                    finalResponse = GetItemsResponse(if (it.isEmpty()) "empty" else "filled", it)
                }
            } catch (e: Exception) {
                e.message?.let {
                    finalResponse = GetItemsResponse(if (it.contains("Unable to resolve host \"content.guardianapis.com\": No address associated with hostname")) "no internet" else "error", listOf())
                }
            }
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