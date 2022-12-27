package com.example.cft1.DTOs

import kotlinx.serialization.*

@Serializable
data class BINInfoDTO(
    var scheme: String? = null,
    var number: Number,
    var type: String? = null,
    var brand: String? = null,
    var prepaid: Boolean? = null,
    var country: Country,
    var bank: Bank
)
