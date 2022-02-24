package com.example.data.network.model

import com.example.domain.Content
import com.example.domain.Fields
import com.example.domain.utils.DomainMapper
import javax.inject.Inject

class ContentApiMapper @Inject constructor() : DomainMapper<ContentApi, Content> {
    override fun mapToDomainModel(model: ContentApi): Content {
        return Content(
            model.id,
            Fields(
                model.fields.body,
                model.fields.thumbnail,
                model.fields.headline
            ),
            model.webPublicationDate
        )
    }

    override fun mapFromDomainModel(model: Content): ContentApi {
        return ContentApi(
            model.id,
            FieldsApi(
                model.fields.body,
                model.fields.thumbnail,
                model.fields.headline
            ),
            model.webPublicationDate
        )
    }

    fun fromEntityList(initial: List<ContentApi>): List<Content> {
        return initial.map { mapToDomainModel(it) }
    }

    fun toEntity(initial: ContentApi): Content {
        return mapToDomainModel(initial)
    }
}