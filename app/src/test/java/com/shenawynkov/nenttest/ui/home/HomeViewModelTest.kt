package com.shenawynkov.nenttest.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.shenawynkov.domain.common.Resource
import com.shenawynkov.domain.model.section.Section
import com.shenawynkov.domain.model.section.SectionDetail
import com.shenawynkov.domain.usecases.SectionUseCase
import com.shenawynkov.domain.usecases.SectionsUseCase
import com.shenawynkov.nenttest.ui.section.SectionViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain


import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    private lateinit var SUT: HomeViewModel
    private val sectionsUseCase: SectionsUseCase = mockk()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(testDispatcher)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {

        Dispatchers.setMain(testDispatcher)

        SUT = HomeViewModel(sectionsUseCase)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun getSection_Success_DataReturned() = testScope.runBlockingTest {
        //Arrange
        val section = Section("href", "1", "ss", "tt")
        val sections = arrayListOf(section)
        val flow = flow<Resource<List<Section>>> {

            emit(Resource.Success(sections))

        }
        coEvery {
            sectionsUseCase.invoke()
        } returns flow

        //Act
        SUT.getSections()
        //Assert
        assertEquals(SUT.sections.value, sections)
        assertEquals(null, SUT.errorMessage.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getSection_Error_DataReturnedFromDatabase() = testScope.runBlockingTest {
        //Arrange
        val section = Section("href", "1", "ss", "tt")
        val sections = arrayListOf(section)
        val flow = flow<Resource<List<Section>>> {

            emit(Resource.Error("error",sections))

        }
        coEvery {
            sectionsUseCase.invoke()
        } returns flow

        //Act
        SUT.getSections()
        //Assert
        assertEquals(SUT.sections.value, sections)
        assertEquals("error", SUT.errorMessage.value)
    }
}
