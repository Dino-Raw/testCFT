package com.example.testcft.interfaces

import com.example.testcft.model.Card
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface API {
    @GET
    suspend fun getCardData(@Url url: String): Card?
}