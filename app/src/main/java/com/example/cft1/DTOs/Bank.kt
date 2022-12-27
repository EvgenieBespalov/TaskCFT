package com.example.cft1.DTOs

import kotlinx.serialization.Serializable

@Serializable
data class Bank(
    var name: String? = null,
    var url: String? = null,
    var phone: String? = null,
    var city: String? = null)