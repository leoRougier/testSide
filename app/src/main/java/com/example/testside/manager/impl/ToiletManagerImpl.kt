package com.example.testside.manager.impl

import com.example.testside.manager.ToiletManager
import com.example.testside.model.Toilets
import com.example.testside.service.ToiletCacheService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ToiletManagerImpl(private val toiletCacheService: ToiletCacheService) : ToiletManager {


    override fun getCachedToilets(): Single<Toilets> = toiletCacheService.getCachedToilets()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}