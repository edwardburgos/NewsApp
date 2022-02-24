package com.example.usecases

import com.example.data.repository.ItemRepositoryImpl
import com.example.data.repository.model.GetItemResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItemUseCaseImpl @Inject constructor(private val itemRepository: ItemRepositoryImpl) :
    GetItemUseCase {

    override fun invoke(queryPath: String): Flow<GetItemResponse> = itemRepository.getItem(queryPath)
}