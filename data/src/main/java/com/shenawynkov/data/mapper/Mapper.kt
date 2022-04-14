package com.shenawynkov.data.mapper

import com.shenawynkov.data.db.SectionEntity
import com.shenawynkov.data.db.SectionEntityDetailUpdate
import com.shenawynkov.data.db.SectionEntityUpdate
import com.shenawynkov.domain.model.section.Section
import com.shenawynkov.domain.model.section.SectionDetail
import com.shenawynkov.domain.model.section.Sections.ViaplaySection


fun List<ViaplaySection>.mapToSectionList(): List<Section> {
    return map { section ->
        Section(
            href = section.href,
            name = section.name,
            id = section.id,
            title = section.title
        )
    }
}

@JvmName("mapToSectionListSectionEntity")
fun List<SectionEntity>.mapToSectionList(): List<Section> {
    return map { section ->
        Section(
            href = section.href ?: "",
            name = section.name,
            id = section.id,
            title = section.title,
            fav = section.fav
        )
    }
}

fun Section.mapToSectionEntity(): SectionEntity {
    return SectionEntity(id = id, href = href, name = name, title = title, description = null, fav = fav)
}

fun Section.mapToSectionEntityUpdate(): SectionEntityUpdate {
    return SectionEntityUpdate(id = id, href = href, name = name, title = title)
}

fun SectionEntity.mapToSectionDetail(): SectionDetail {
    return SectionDetail(sectionId = id, title = title, description = description ?: "", fav = fav)
}

fun SectionDetail.mapToSectionEntityUpdate(): SectionEntityDetailUpdate {
    return SectionEntityDetailUpdate(id = sectionId, description = description)
}


