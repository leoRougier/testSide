package com.example.testside.app.map.listfragment

import android.util.Log
import com.example.testside.architecture.BasePresenter
import com.example.testside.manager.ToiletManager
import com.example.testside.model.Record
import com.example.testside.model.Toilets

class ListFragmentPresenter(private val toiletManager: ToiletManager):BasePresenter<ListFragmentView>() {
    fun getToilets() {
        toiletManager.getToilets()
            .doOnSubscribe { lifecycleDisposable(it) }
            .subscribe({ toilets: Toilets ->
                Log.i("toilets", toilets.toString())
                view?.showToilet(toilets.records  as MutableList<Record>)

                //clusterAddItem(toilets.records)
            }, {
                view?.error()
            })    }
}