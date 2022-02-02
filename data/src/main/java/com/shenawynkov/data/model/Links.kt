package com.shenawynkov.domain.model.Sections

import com.google.gson.annotations.SerializedName
import com.shenawynkov.domain.model.section.Sections.ViaplaySection


data class Links(

    @SerializedName("viaplay:sections")
    val viaplaySections: List<ViaplaySection>

)