package com.shenawynkov.domain.repositories

import com.shenawynkov.domain.common.Resource
import com.shenawynkov.domain.model.section.Section
import com.shenawynkov.domain.model.section.SectionDetail
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Url


interface SectionsRepo {
     fun getSections(): Flow<Resource<List<Section>>>
     suspend fun getSectionsFromDb(): List<Section>
     suspend fun getFavSections(): List<Section>
     suspend fun updateFav(fav:Boolean,id:String)
     fun getSection(id:String,url: String): Flow<Resource<SectionDetail?>>
}