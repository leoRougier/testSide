package com.example.testside.app.map.mapfragment

import com.example.testside.architecture.BasePresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

class MapFragmentPresenter:BasePresenter<MapFragmentView>() {

    val ileDeFranceLatLng = LatLng(48.864716, 2.349014)

    fun initMap(map: GoogleMap) {
        map.mapType = GoogleMap.MAP_TYPE_NORMAL

       // mMap.clear() //clear old markers

        val cameraPosition = CameraPosition.builder()
            .target(ileDeFranceLatLng)
            .zoom(10f)
            .bearing(0f)
            .tilt(45f)
            .build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 10000, null)
    }
}