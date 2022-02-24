package com.example.usecases

import com.example.data.repository.model.GetItemResponse
import kotlinx.coroutines.flow.Flow

interface GetItemUseCase {
    operator fun invoke(queryPath: String): Flow<GetItemResponse>
}