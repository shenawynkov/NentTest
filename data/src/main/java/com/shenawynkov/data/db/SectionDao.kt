package com.shenawynkov.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shenawynkov.domain.model.section.Section

@Dao
interface SectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSection(vararg sectionEntity: SectionEntity)


    @Query("SELECT * FROM section WHERE id=:id")
    suspend fun getSection(id: String): SectionEntity


    @Query("SELECT * FROM section")
    suspend fun getAllSections(): List<SectionEntity>

}