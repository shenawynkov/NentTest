package com.shenawynkov.nenttest.ui.section

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shenawynkov.domain.model.section.Section
import com.shenawynkov.domain.usecases.SectionsUseCase
import com.shenawynkov.domain.common.Resource
import com.shenawynkov.domain.model.section.SectionDetail
import com.shenawynkov.domain.usecases.SectionUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SectionViewModel @Inject constructor(val sectionUseCase: SectionUseCase) : ViewModel() {
    val loading = MutableLiveData(false)
    val errorMessage = MutableLiveData<String>()
    val sectionDetail = MutableLiveData<SectionDetail>()


    fun getSection(section: Section) {


        sectionUseCase.invoke(section.id, section.href).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    loading.value = false
                    sectionDetail.value = result.data

                }
                is Resource.Error -> {
                    loading.value = false
                    errorMessage.value = result.message
                    result.data?.let {
                        sectionDetail.value = it
                    }

                }
                is Resource.Loading -> {
                    loading.value = true

                }
            }


        }.launchIn(viewModelScope)

    }
}