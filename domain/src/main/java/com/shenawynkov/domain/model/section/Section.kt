package com.shenawynkov.domain.model.section

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Section(
    val href: String,
    val id: String,
    val name: String,
    val title: String
):Parcelable
