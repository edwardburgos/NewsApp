package com.example.data.repository.model

import com.example.data.network.model.ContentApi

data class GetItemsResponse (
    val status: String,
    val items: List<ContentApi>
)