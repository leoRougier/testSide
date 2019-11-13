package com.example.testside.manager

import com.example.testside.model.Toilets
import io.reactivex.Single

interface ToiletManager {

    fun getCachedToilets():Single<Toilets>

}