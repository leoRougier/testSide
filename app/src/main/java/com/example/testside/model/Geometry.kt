package com.example.testside.model

import com.google.android.gms.maps.model.LatLng

data class Geometry(val coordinates:List<Double>? = null, var latLng: LatLng? = null) {
}