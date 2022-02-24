package com.example.usecases

import com.example.data.repository.model.GetItemsResponse
import kotlinx.coroutines.flow.Flow

interface GetItemsUseCase {
    operator fun invoke(query: String?, section: String, tag: String?): Flow<GetItemsResponse>
}