package com.shenawynkov.data.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [SectionDetailEntity::class,SectionEntity::class], version = 1)
abstract class SectionDatabase : RoomDatabase() {

    abstract fun sectionDoa(): SectionDao
    abstract fun sectionDetailDoa(): SectionDetailDao

}