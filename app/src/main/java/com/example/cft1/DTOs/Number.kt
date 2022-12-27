package com.example.cft1.DTOs

import kotlinx.serialization.Serializable

@Serializable
data class Number(
    var length: Int? = null,
    var luhn: Boolean? = null)
