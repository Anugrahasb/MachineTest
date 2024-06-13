package com.example.machinetest.api

import com.example.machinetest.model.HomePageContent
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("Todo")
    suspend fun getHomePageData(): Response<List<HomePageContent>>
}
