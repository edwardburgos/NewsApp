package com.example.newsapp.detail

import androidx.lifecycle.ViewModel
import com.example.data.repository.model.GetItemResponse
import com.example.usecases.GetItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getItemApi: GetItemUseCase
) : ViewModel() {

    lateinit var getItem: Flow<GetItemResponse>

    fun setItemId(newValue: String) {
        getItem = getItemApi.invoke(newValue)
    }
}
