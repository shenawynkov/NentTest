package com.shenawynkov.nenttest.di

import android.content.Context
import androidx.room.Room
import com.shenawynkov.data.db.SectionDatabase
import com.shenawynkov.domain.model.section.Section

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context) = Room.databaseBuilder(
        applicationContext,
        SectionDatabase::class.java, "sectionDB"
    )	.fallbackToDestructiveMigration()
        .build()


}