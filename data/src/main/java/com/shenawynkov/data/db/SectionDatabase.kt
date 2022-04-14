package com.shenawynkov.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase


@Database(

    entities = [SectionEntity::class], version = 2
)
abstract class SectionDatabase : RoomDatabase() {

    abstract fun sectionDoa(): SectionDao


}