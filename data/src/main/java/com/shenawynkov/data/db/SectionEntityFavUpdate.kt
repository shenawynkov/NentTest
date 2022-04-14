package com.shenawynkov.data.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "section")
data class SectionEntityFavUpdate(

    @PrimaryKey  val id: String,
    @ColumnInfo(name = "fav") val fav: Boolean=false

    )