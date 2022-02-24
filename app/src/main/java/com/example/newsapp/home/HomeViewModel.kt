package com.example.newsapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.network.model.ContentApiMapper
import com.example.domain.Content
import com.example.newsapp.sections
import com.example.usecases.GetItemsUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getItemsApi: GetItemsUseCaseImpl,
    private val apiMapper: ContentApiMapper
) : ViewModel() {

    private var currentFlow = viewModelScope.launch { }

    private val _query = MutableLiveData<String?>()
    val query: LiveData<String?>
        get() = _query

    private val _section = MutableLiveData(0)
    val section: LiveData<Int>
        get() = _section

    private val _items = MutableLiveData<List<Content>>()
    val items: LiveData<List<Content>>
        get() = _items

    private val _navigateToSelectedItem = MutableLiveData<String?>()
    val navigateToSelectedItem: LiveData<String?>
        get() = _navigateToSelectedItem

    private val _responseState = MutableLiveData("initial")
    val responseState: LiveData<String>
        get() = _responseState

    private val _currentTag = MutableLiveData<String?>()
    val currentTag: LiveData<String?>
        get() = _currentTag

    fun updateCurrentTag(newValue: String?) {
        _currentTag.value = newValue
        _section.value?.let {
            getItemsFromFlow(_query.value, sections.elementAt(it).id, newValue)
        }
    }

    fun updateQuery(newValue: String) {
        _query.value = newValue
    }

    fun updateSection(newValue: Int) {
        _section.value = newValue
    }

    init {
        _section.value?.let {
            getItemsFromFlow(_query.value, sections.elementAt(it).id, _currentTag.value)
        }
    }

    fun getItemsFromFlow(query: String?, section: String, currentTag: String?) {
        currentFlow.cancel()
        currentFlow = viewModelScope.launch {
            getItemsApi(query, section, currentTag).collect {
                if (_items.value == null || _items.value?.size == 0 ||
                    _items.value != apiMapper.fromEntityList(it.items) ||
                    it.status != "initial"
                ) {
                    _responseState.value = it.status
                    _items.value = apiMapper.fromEntityList(it.items)
                }
            }
        }
    }

    fun displayItemDetails(itemId: String) {
        _navigateToSelectedItem.value = itemId
    }

    fun displayItemDetailsComplete() {
        _navigateToSelectedItem.value = null
    }
}