package com.example.newsapp.usecases

import com.example.data.network.model.ContentApi
import com.example.data.network.model.FieldsApi
import com.example.data.repository.model.GetItemsResponse
import com.example.usecases.GetItemsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetItemsUseCaseFake: GetItemsUseCase {
    override fun invoke(query: String?, section: String, tag: String?): Flow<GetItemsResponse> {
        return flow {
            if (query == "\"query1\"" && section == "business" && tag == null) {
                emit(GetItemsResponse(status = "filled", items = listOf(
                    ContentApi(id = "id1", fields = FieldsApi(body = null, thumbnail = "Thumbnail 1", headline = "Headline 1"), webPublicationDate = "Date 1"),
                    ContentApi(id = "id2", fields = FieldsApi(body = null, thumbnail = "Thumbnail 2", headline = "Headline 2"), webPublicationDate = "Date 2"),
                )))
            }
        }
    }
}