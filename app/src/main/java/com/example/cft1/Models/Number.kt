package com.example.cft1.Models

import kotlinx.serialization.Serializable

@Serializable
data class Number(
    val length: Int? = null,
    val luhn: Boolean? = null)
