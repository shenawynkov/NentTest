package com.shenawynkov.data.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "section")
data class SectionEntityDetailUpdate(

    @PrimaryKey  val id: String,

    @ColumnInfo(name = "description") val description: String?,



    )