package com.example.newsapp.usecases

import com.example.data.network.model.ContentApi
import com.example.data.network.model.FieldsApi
import com.example.data.repository.model.GetItemResponse
import com.example.usecases.GetItemUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetItemUseCaseFake: GetItemUseCase {
    override fun invoke(queryPath: String): Flow<GetItemResponse> {
        return flow {
            when (queryPath) {
                "id1" -> emit(GetItemResponse("successful", ContentApi(id = "id1", fields = FieldsApi(body = "body1", thumbnail = null, headline = "Headline 1"), webPublicationDate = "date1")))
                "id2" -> emit(GetItemResponse("successful", ContentApi(id = "id2", fields = FieldsApi(body = "body2", thumbnail = "image2", headline = "Headline 2"), webPublicationDate = "data2")))
                "id3" -> emit(GetItemResponse("successful", ContentApi(id = "id3", fields = FieldsApi(body = "body3", thumbnail = null, headline = "Headline 3"), webPublicationDate = "date3")))
            }
        }
    }
}