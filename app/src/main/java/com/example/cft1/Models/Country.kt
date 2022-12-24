package com.example.cft1.Models

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    var numeric: String? = null,
    var alpha2: String? = null,
    val name: String? = null,
    val emoji: String? = null,
    val currency: String? = null,
    val latitude: Int? = null,
    val longitude: Int? = null)
