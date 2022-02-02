package com.shenawynkov.nenttest.ui.section

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shenawynkov.domain.usecases.SectionUseCase
import com.shenawynkov.domain.usecases.SectionsUseCase
import com.shenawynkov.nenttest.ui.home.HomeViewModel
import javax.inject.Inject


class SectionViewModelFactory
@Inject constructor(private val sectionUseCase: SectionUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SectionViewModel(
            sectionUseCase
        ) as T
    }


}