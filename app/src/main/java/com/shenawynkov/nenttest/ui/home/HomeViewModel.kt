package com.shenawynkov.nenttest.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shenawynkov.domain.model.section.Section
import com.shenawynkov.domain.usecases.SectionsUseCase
import com.shenawynkov.domain.common.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class HomeViewModel @Inject constructor(val sectionsUseCase: SectionsUseCase) : ViewModel() {
    val loading = MutableLiveData(false)
    val errorMessage = MutableLiveData<String>()
    val sections = MutableLiveData<List<Section>>()


    fun getSections() {
        sectionsUseCase.invoke().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    loading.value = false
                    sections.value = result.data

                }
                is Resource.Error -> {
                    loading.value = false
                    errorMessage.value = result.message
                    result.data?.let {
                        sections.value = it
                    }
                }
                is Resource.Loading -> {
                    loading.value = true

                }
            }


        }.launchIn(viewModelScope)
    }
}