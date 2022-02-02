package com.shenawynkov.domain.repositories

import com.shenawynkov.domain.common.Resource
import com.shenawynkov.domain.model.section.Section
import com.shenawynkov.domain.model.section.SectionDetail
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Url


interface SectionsRepo {
     fun getSections(): Flow<Resource<List<Section>>>
     fun getSection(id:String,url: String): Flow<Resource<SectionDetail?>>
}