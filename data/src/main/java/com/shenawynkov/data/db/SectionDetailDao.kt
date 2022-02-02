package com.shenawynkov.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shenawynkov.domain.model.section.Section

@Dao
interface SectionDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSection(vararg sectionDetailEntity: SectionDetailEntity)

    @Query("SELECT * FROM sectionDetail WHERE id=:id")
    suspend fun getSection(id:String):SectionDetailEntity?



}