package com.example.newsapp.detail

import com.example.newsapp.usecases.GetItemUseCaseFake
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        viewModel = DetailViewModel(
            GetItemUseCaseFake()
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `testing getItem`() = runBlocking {
            var result = ""
            viewModel.setItemId("id1")
            viewModel.getItem.collect {
                it.item?.fields?.let {
                    result = it.headline
                }
            }
            TestCase.assertEquals(
                "Headline 1",
                result
            )
    }
}