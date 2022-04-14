package com.shenawynkov.nenttest.ui.section

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shenawynkov.domain.common.Resource
import com.shenawynkov.domain.model.section.Section
import com.shenawynkov.domain.model.section.SectionDetail
import com.shenawynkov.domain.usecases.FavouriteUseCase
import com.shenawynkov.domain.usecases.SectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SectionViewModel @Inject constructor(
    private val sectionUseCase: SectionUseCase,
    private val favouriteUseCase: FavouriteUseCase
) : ViewModel() {
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

    fun updateFav() {
        sectionDetail.value?.let {
            sectionDetail.value = it.copy(fav = !it.fav)

            viewModelScope.launch {

                favouriteUseCase.invoke(!it.fav, it.sectionId)


            }
        }
    }
}