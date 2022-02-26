package com.example.newsapp.home

import com.example.data.network.model.ContentApi
import com.example.data.network.model.ContentApiMapper
import com.example.newsapp.detail.DetailViewModel
import com.example.newsapp.usecases.GetItemsUseCaseFake
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel(
            GetItemsUseCaseFake(),
            ContentApiMapper()
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `testing getItems`() = runBlocking {
        var result = listOf<ContentApi>()
        viewModel.setQuery("query1")
        viewModel.setSection(0)
        viewModel.getItems.collect {
            result = it.items
        }
        TestCase.assertEquals(
            2,
            result.size
        )
        TestCase.assertEquals(
            "Headline 2",
            result.elementAt(1).fields.headline
        )
    }
}

