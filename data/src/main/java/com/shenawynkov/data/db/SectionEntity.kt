package com.shenawynkov.data.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "section")
data class SectionEntity(

    @PrimaryKey  val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "href") val href: String,
    @ColumnInfo(name = "name") val name: String,
)