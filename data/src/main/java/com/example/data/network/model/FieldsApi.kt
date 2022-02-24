package com.example.data.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FieldsApi(
    var body: String?,
    var thumbnail: String?,
    var headline: String
) : Parcelable