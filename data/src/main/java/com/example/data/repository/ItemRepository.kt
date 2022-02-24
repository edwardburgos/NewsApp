package com.example.data.repository

import com.example.data.repository.model.GetItemResponse
import com.example.data.repository.model.GetItemsResponse
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getItem(queryPath: String): Flow<GetItemResponse>
    fun getItems(query: String?, section: String, tag: String?): Flow<GetItemsResponse>
}