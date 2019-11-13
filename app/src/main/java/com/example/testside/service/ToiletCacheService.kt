package com.example.testside.service

import com.example.testside.model.Toilets
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ToiletCacheService {
    @GET("search/")
    fun getCachedToilets(@Query("dataset") ts: String = "sanisettesparis2011",
                         @Query("rows") offset: Int = 1000): Single<Toilets>

   }