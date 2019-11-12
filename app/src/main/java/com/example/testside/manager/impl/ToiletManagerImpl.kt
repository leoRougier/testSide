package com.example.testside.manager.impl

import com.example.testside.manager.ToiletManager
import com.example.testside.model.Toilets
import com.example.testside.service.ToiletService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ToiletManagerImpl(private val toiletService: ToiletService) : ToiletManager {
    override fun getToilets(): Single<Toilets> = toiletService.getToilets()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}