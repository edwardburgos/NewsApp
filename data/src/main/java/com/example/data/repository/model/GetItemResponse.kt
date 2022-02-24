package com.example.data.repository.model

import com.example.data.network.model.ContentApi

data class GetItemResponse (
    val status: String,
    val item: ContentApi?
)