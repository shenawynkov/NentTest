package com.shenawynkov.domain.model.section.Sections

import com.shenawynkov.domain.model.Sections.Links


data class SectionsResponse(

    val _links: Links,
    val description: String,
    val pageType: String,
    val title: String,

    )