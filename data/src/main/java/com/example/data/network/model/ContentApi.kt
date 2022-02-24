package com.example.data.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContentApi(
    var id: String,
    var fields: FieldsApi,
    var webPublicationDate: String
) : Parcelable