package com.shenawynkov.nenttest.di

import com.shenawynkov.data.apiService.ApiService
import com.shenawynkov.data.db.SectionDatabase
import com.shenawynkov.data.repo.SectionsRepoImpl
import com.shenawynkov.domain.repositories.SectionsRepo

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    @Singleton
    fun provideHomeRepository(apiService: ApiService,db:SectionDatabase): SectionsRepo {
        return SectionsRepoImpl(apiService,db)
    }

}