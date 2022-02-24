package com.example.usecases

import com.example.data.repository.ItemRepositoryImpl
import com.example.data.repository.model.GetItemsResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItemsUseCaseImpl @Inject constructor(private val itemRepository: ItemRepositoryImpl) :
    GetItemsUseCase {

    override fun invoke(query: String?, section: String, tag: String?): Flow<GetItemsResponse> = itemRepository.getItems(query, section, tag)
}