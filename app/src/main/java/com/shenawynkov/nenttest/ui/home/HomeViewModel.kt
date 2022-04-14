package com.shenawynkov.nenttest.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shenawynkov.domain.common.Resource
import com.shenawynkov.domain.model.section.Section
import com.shenawynkov.domain.usecases.FavouriteUseCase
import com.shenawynkov.domain.usecases.SectionsUseCase
import com.shenawynkov.domain.usecases.SyncSectionsDBUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sectionsUseCase: SectionsUseCase,
    private val favouriteUseCase: FavouriteUseCase,
    private val syncSectionsDBUseCase: SyncSectionsDBUseCase
) : ViewModel() {
    val loading = MutableLiveData(false)
    val errorMessage = MutableLiveData<String>()
    val sections = MutableLiveData<List<Section>>()
    var backStackStatus = false

    init {
        getSections()
    }

    private fun getSections() {
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

    fun updateFav(id: String, fav: Boolean) {
        viewModelScope.launch {
            favouriteUseCase.invoke(fav, id)

        }
    }

    fun syncSections() {
        if (backStackStatus) {
            viewModelScope.launch {
                sections.value = syncSectionsDBUseCase.invoke()
            }
        }
        backStackStatus=false

    }
}