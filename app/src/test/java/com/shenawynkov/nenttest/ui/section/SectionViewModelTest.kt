package com.shenawynkov.nenttest.ui.section

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.shenawynkov.domain.common.Resource
import com.shenawynkov.domain.model.section.Section
import com.shenawynkov.domain.model.section.SectionDetail
import com.shenawynkov.domain.usecases.SectionUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


class SectionViewModelTest {
    private lateinit var SUT: SectionViewModel
    private val sectionUseCase: SectionUseCase = mockk()

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
        SUT = SectionViewModel(sectionUseCase)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun getSection_Success_DataReturned() = testScope.runBlockingTest {
        //Arrange
        val section = SectionDetail("title", "desc", "1")
        val flow = flow<Resource<SectionDetail?>> {
            emit(Resource.Success(section))
        }

        coEvery {
            sectionUseCase.invoke("1", any())
        } returns
                flow


        //Act
        SUT.getSection(Section("s", "1", "s", "s"))
        //Assert
        assertEquals(section, SUT.sectionDetail.value)
        assertEquals(null, SUT.errorMessage.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getSection_Error_DataReturnedFromDatabase() = testScope.runBlockingTest {
        //Arrange
        val section = SectionDetail("title", "desc", "1")
        val flow = flow<Resource<SectionDetail?>> {
            emit(Resource.Error("error",section))
        }

        coEvery {
            sectionUseCase.invoke("1", any())
        } returns flow

        //Act
        SUT.getSection(Section("s", "1", "s", "s"))
        //Assert
        assertEquals(section, SUT.sectionDetail.value)
        assertEquals("error", SUT.errorMessage.value)
    }
}