package com.example.testcft.model

import com.squareup.moshi.Json

data class Card (
    @field:Json(name = "number") val number: Number?,
    @field:Json(name = "scheme") val scheme: String?,
    @field:Json(name = "type") val type: String?,
    @field:Json(name = "brand") val brand: String?,
    @field:Json(name = "prepaid") val prepaid: Boolean?,
    @field:Json(name = "country") val country: Country?,
    @field:Json(name = "bank") val bank: Bank?,
) {
    data class Number (
        @field:Json(name = "length") val length: Int?,
        @field:Json(name = "luhn") val luhn: Boolean?,
    )

    data class Country (
        @field:Json(name = "numeric") val numeric: String?,
        @field:Json(name = "alpha2") val alpha2: String?,
        @field:Json(name = "name") val name: String?,
        @field:Json(name = "emoji") val emoji: String?,
        @field:Json(name = "currency") val currency: String?,
        @field:Json(name = "latitude") val latitude: Int?,
        @field:Json(name = "longitude") val longitude: Int?,
    )

    data class Bank (
        @field:Json(name = "name") val name: String?,
        @field:Json(name = "url") val url: String?,
        @field:Json(name = "phone") val phone: String?,
        @field:Json(name = "city") val city: String?,
    )
}
