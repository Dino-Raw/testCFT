package com.example.testcft.objects

import com.example.testcft.interfaces.API
import com.example.testcft.objects.Const.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Client {
    var retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(API::class.java)
}