package com.shenawynkov.data.repo

import com.shenawynkov.data.apiService.ApiService
import com.shenawynkov.data.db.SectionDatabase
import com.shenawynkov.data.db.SectionEntityFavUpdate
import com.shenawynkov.data.mapper.mapToSectionDetail
import com.shenawynkov.data.mapper.mapToSectionEntity
import com.shenawynkov.data.mapper.mapToSectionEntityUpdate
import com.shenawynkov.data.mapper.mapToSectionList
import com.shenawynkov.domain.common.Resource
import com.shenawynkov.domain.model.section.Section
import com.shenawynkov.domain.model.section.SectionDetail
import com.shenawynkov.domain.repositories.SectionsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


class SectionsRepoImpl(private val apiService: ApiService, private val db: SectionDatabase) :
    SectionsRepo {
    private suspend fun getSectionsFromRemote(): List<Section> {
        return apiService.getSections()._links.viaplaySections.mapToSectionList()
    }

     override suspend fun getSectionsFromDb(): List<Section> {
        return db.sectionDoa().getAllSections().mapToSectionList()
    }

    override suspend fun getFavSections(): List<Section> {
        return db.sectionDoa().getAllFavSections().mapToSectionList()
    }

    override fun getSections(): Flow<Resource<List<Section>>> {

        return flow {
            try {
                //emit loading until receiving data
                emit(Resource.Loading<List<Section>>())
                //emit  data
                val list = getSectionsFromRemote()
                //updateDb
                withContext(Dispatchers.IO)
                {
                    updateSections(list)
                }
                emit(Resource.Success(getSectionsFromDb()))


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

    override suspend fun updateFav(fav: Boolean, id: String) {
        withContext(Dispatchers.IO)
        {
            db.sectionDoa().updateFav(SectionEntityFavUpdate(id, fav))
        }

    }

    override fun getSection(id: String, url: String): Flow<Resource<SectionDetail?>> {


        return flow {
            try {
                //emit loading until receiving data
                emit(Resource.Loading<SectionDetail?>())
                //emit  data
                val section = getSectionFromRemote(url)
                //updateDb
                withContext(Dispatchers.IO)
                {
                    updateSection(section)
                }
                emit(Resource.Success(getSectionFromDb(section.sectionId)))


            } catch (e: HttpException) {
                emit(
                    Resource.Error<SectionDetail?>(
                        e?.localizedMessage ?: "An unexpected error occurred", getSectionFromDb(id)
                    )
                )
            } catch (e: IOException) {
                emit(
                    Resource.Error<SectionDetail?>(
                        "Couldn't reach server. Check your internet connection.",
                        getSectionFromDb(id)
                    )
                )
            }
        }
    }

    private suspend fun getSectionFromDb(id: String): SectionDetail? {
        return db.sectionDoa().getSection(id).mapToSectionDetail()
    }

    private suspend fun getSectionFromRemote(url: String): SectionDetail {

        return apiService.getSectionByUrl(url.replace("{?dtg}", ""))
    }

    private suspend fun updateSections(list: List<Section>) {
        list.forEach {
            val updated = db.sectionDoa().updateSection(it.mapToSectionEntityUpdate())
            if (updated == 0)
                db.sectionDoa().saveSection(it.mapToSectionEntity())
        }
    }

    private suspend fun updateSection(sectionDetail: SectionDetail) {

        db.sectionDoa().updateSectionDetail(sectionDetail.mapToSectionEntityUpdate())

    }
}