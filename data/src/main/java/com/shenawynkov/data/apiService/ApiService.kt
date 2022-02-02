package com.shenawynkov.data.apiService

import com.shenawynkov.data.Constants.sections
import com.shenawynkov.domain.model.section.SectionDetail

import com.shenawynkov.domain.model.section.Sections.SectionsResponse
import retrofit2.http.GET
import retrofit2.http.Url


interface ApiService {

    @GET(sections)
    suspend fun getSections(): SectionsResponse

    @GET
    suspend fun getSectionByUrl(@Url url: String): SectionDetail
}