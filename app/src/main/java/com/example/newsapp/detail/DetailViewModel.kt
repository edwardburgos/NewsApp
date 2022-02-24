package com.example.newsapp.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.network.model.ContentApiMapper
import com.example.domain.Content
import com.example.usecases.GetItemUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getItemApi: GetItemUseCaseImpl,
    private val apiMapper: ContentApiMapper
) : ViewModel() {

    private val _responseState = MutableLiveData("initial")
    val responseState: LiveData<String>
        get() = _responseState

    private val _item = MutableLiveData<Content?>()
    val item: LiveData<Content?>
        get() = _item

    private var currentFlow = viewModelScope.launch { }

    init {
        getItemFromFlow("")
    }

    fun getItemFromFlow(id: String) {
        currentFlow.cancel()
        currentFlow = viewModelScope.launch {
            getItemApi(id).collect {
                val mapped = it.item?.let { it1 ->  apiMapper.toEntity(it1) } ?: run { null }
                if (_item.value == null || _item.value != mapped
                    || it.status != "initial"
                ) {
                    _responseState.value = it.status
                    _item.value = mapped
                }
            }
        }
    }
}