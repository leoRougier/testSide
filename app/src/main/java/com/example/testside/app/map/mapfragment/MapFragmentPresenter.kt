package com.example.testside.app.map.mapfragment

import android.util.Log
import com.example.testside.architecture.BasePresenter
import com.example.testside.manager.ToiletManager
import com.example.testside.model.Toilets
import com.google.android.gms.maps.model.LatLng


class MapFragmentPresenter(private val toiletManager: ToiletManager) :
    BasePresenter<MapFragmentView>() {

    private val mIleDeFranceLatLng = LatLng(48.864716, 2.349014)

   /* fun initMap(map: GoogleMap) {
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.clear()
        val cameraPosition = CameraPosition.builder()
            .target(mIleDeFranceLatLng)
            .zoom(10f)
            .bearing(0f)
            .tilt(45f)
            .build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 10000, null)
    }*/

    fun getToilets() {
        toiletManager.getCachedToilets()
            .doOnSubscribe { lifecycleDisposable(it) }
            .subscribe({ toilets: Toilets ->
                Log.i("toilets", toilets.toString())

                view?.clusterAddItem(toilets.records)
            }, {
                view?.error()
            })
    }
}