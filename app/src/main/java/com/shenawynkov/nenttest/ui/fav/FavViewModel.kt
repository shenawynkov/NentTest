package com.shenawynkov.nenttest.ui.fav

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
class FavViewModel @Inject constructor(

    private val favouriteUseCase: FavouriteUseCase

) : ViewModel() {

    val sections = MutableLiveData<List<Section>>()

    init {
        getSections()
    }

    private fun getSections() {
        viewModelScope.launch {
            sections.value=  favouriteUseCase.getFavSections()
        }

    }

    fun updateFav(id: String, fav: Boolean) {
        viewModelScope.launch {
            favouriteUseCase.invoke(fav, id)

        }
    }

}