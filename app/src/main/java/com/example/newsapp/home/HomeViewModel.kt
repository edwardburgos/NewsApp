package com.example.newsapp.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.data.network.model.ContentApiMapper
import com.example.data.repository.model.GetItemsResponse
import com.example.newsapp.sections
import com.example.usecases.GetItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getItemsApi: GetItemsUseCase,
    val apiMapper: ContentApiMapper
) : ViewModel() {

    var query = mutableStateOf("")
    var section = mutableStateOf(0)
    var tag = mutableStateOf<String?>(null)

    var getItems = updateGetItemsValue()

    fun updateGetItemsValue(): Flow<GetItemsResponse> {
        return getItemsApi.invoke(
            query = if (query.value == "") null else "\"${query.value}\"",
            section = sections.elementAt(section.value).id,
            tag = tag.value,
        )
    }

    fun setQuery(newValue: String) {
        query.value = newValue.replace("\n", "")
        getItems = updateGetItemsValue()
    }

    fun setSection(newValue: Int) {
        section.value = newValue
        getItems = updateGetItemsValue()
    }

    fun setTag(newValue: String?) {
        tag.value = newValue
        getItems = updateGetItemsValue()
    }
}