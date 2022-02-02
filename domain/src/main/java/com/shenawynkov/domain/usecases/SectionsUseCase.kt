package com.shenawynkov.domain.usecases

import com.shenawynkov.domain.repositories.SectionsRepo
import com.shenawynkov.domain.model.section.Section
import com.shenawynkov.domain.common.Resource
import com.shenawynkov.domain.model.section.SectionDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.http.Url
import java.io.IOException
import javax.inject.Inject


class SectionsUseCase @Inject constructor(private val repo: SectionsRepo) {
    operator fun invoke(): Flow<Resource<List<Section>>> = repo.getSections()

}