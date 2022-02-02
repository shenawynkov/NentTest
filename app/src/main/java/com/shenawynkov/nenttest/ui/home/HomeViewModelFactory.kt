package com.shenawynkov.nenttest.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shenawynkov.domain.usecases.SectionsUseCase
import javax.inject.Inject


class HomeViewModelFactory
@Inject constructor(private val sectionsUseCase: SectionsUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(
            sectionsUseCase
        ) as T
    }


}