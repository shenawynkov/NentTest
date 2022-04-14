package com.shenawynkov.data.db

import androidx.room.*

@Dao
interface SectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSection(vararg sectionEntity: SectionEntity)


    @Query("SELECT * FROM section WHERE id=:id")
    suspend fun getSection(id: String): SectionEntity


    @Query("SELECT * FROM section")
    suspend fun getAllSections(): List<SectionEntity>
    @Query("SELECT * FROM section WHERE fav=1")
    suspend fun getAllFavSections(): List<SectionEntity>

    @Update(entity = SectionEntity::class)
    suspend fun updateSectionDetail(sectionEntityDetailUpdate: SectionEntityDetailUpdate)

    @Update(entity = SectionEntity::class)
    suspend fun updateFav(favUpdate: SectionEntityFavUpdate)

    @Update(entity = SectionEntity::class)
    suspend fun updateSection(sectionEntityUpdate: SectionEntityUpdate): Int
}