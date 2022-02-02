package com.shenawynkov.data.repo

import android.util.Log
import com.shenawynkov.data.apiService.ApiService
import com.shenawynkov.data.db.SectionDatabase
import com.shenawynkov.data.mapper.mapToSectionDetail
import com.shenawynkov.data.mapper.mapToSectionDetailEntity
import com.shenawynkov.data.mapper.mapToSectionEntity
import com.shenawynkov.data.mapper.mapToSectionList
import com.shenawynkov.domain.common.Resource
import com.shenawynkov.domain.repositories.SectionsRepo
import com.shenawynkov.domain.model.section.Section
import com.shenawynkov.domain.model.section.SectionDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


class SectionsRepoImpl(private val apiService: ApiService, private val db: SectionDatabase) :
    SectionsRepo {
    private suspend fun getSectionsFromRemote(): List<Section> {
        return apiService.getSections()._links.viaplaySections.mapToSectionList()
    }

    private suspend fun getSectionsFromDb(): List<Section> {
        return db.sectionDoa().getAllSections().mapToSectionList()
    }

    override fun getSections(): Flow<Resource<List<Section>>> {

        return flow {
            try {
                //emit loading until receiving data
                emit(Resource.Loading<List<Section>>())
                //emit  data
                val list = getSectionsFromRemote()
                emit(Resource.Success(list))
                //updateDb
                updateSections(list)
            } catch (e: HttpException) {
                emit(
                    Resource.Error<List<Section>>(
                        e.localizedMessage ?: "An unexpected error occurred", getSectionsFromDb()
                    )
                )
            } catch (e: IOException) {
                emit(
                    Resource.Error<List<Section>>(
                        "Couldn't reach server. Check your internet connection.",
                        getSectionsFromDb()
                    )
                )
            }
        }


    }

    override  fun getSection(id: String, url: String): Flow<Resource<SectionDetail?>> {


        return flow {
            try {
                //emit loading until receiving data
                emit(Resource.Loading<SectionDetail?>())
                //emit  data
                val section=getSectionFromRemote(url)
                emit(Resource.Success(section))
                //updateDb
                updateSection(section)
            } catch (e: HttpException) {
                emit(
                    Resource.Error<SectionDetail?>(
                        e?.localizedMessage ?: "An unexpected error occurred",getSectionFromDb(id)
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<SectionDetail?>("Couldn't reach server. Check your internet connection.",getSectionFromDb(id)))
            }
        }
    }

    private suspend fun getSectionFromDb(id: String): SectionDetail? {
        return db.sectionDetailDoa().getSection(id)?.mapToSectionDetail()
    }

    private suspend fun getSectionFromRemote(url: String): SectionDetail {

        return apiService.getSectionByUrl(url.replace("{?dtg}", ""))
    }

    private suspend fun updateSections(list: List<Section>) {
        list.forEach {
            db.sectionDoa().saveSection(it.mapToSectionEntity())
        }
    }

    private suspend fun updateSection(sectionDetail: SectionDetail) {

        db.sectionDetailDoa().saveSection(sectionDetail.mapToSectionDetailEntity())

    }
}