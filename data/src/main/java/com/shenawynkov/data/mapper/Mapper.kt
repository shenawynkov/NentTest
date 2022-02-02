package com.shenawynkov.data.mapper

import com.shenawynkov.data.db.SectionDetailEntity
import com.shenawynkov.data.db.SectionEntity
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
            href = section.href,
            name = section.name,
            id = section.id,
            title = section.title
        )
    }
}

fun Section.mapToSectionEntity(): SectionEntity {
    return SectionEntity(id = id, href = href, name = name, title = title)
}

fun SectionDetail.mapToSectionDetailEntity(): SectionDetailEntity {
    return SectionDetailEntity(id = sectionId, description = description, title = title)
}

fun SectionDetailEntity.mapToSectionDetail(): SectionDetail {
    return SectionDetail(sectionId = id, description = description, title = title)
}

