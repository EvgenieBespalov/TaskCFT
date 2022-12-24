package com.example.cft1.Models

import kotlinx.serialization.*

@Serializable
data class MainData(
    val number: Number,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val country: Country,
    val bank: Bank)
