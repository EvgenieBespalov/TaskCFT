package com.example.cft1.Models

import com.example.cft1.DTOs.Bank
import com.example.cft1.DTOs.Country
import com.example.cft1.DTOs.Number


data class BINInfoModel(
    var BIN: String? = null,
    var scheme: String? = null,
    var number: Number,
    var type: String? = null,
    var brand: String? = null,
    var prepaid: Boolean? = null,
    var country: Country,
    var bank: Bank
)
