package com.example.testside.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class Record(
    val recordId: String? = null,
    val fields: Fields? = null,
    val geometry: Geometry
) : ClusterItem {

    override fun getSnippet(): String? = listOfNotNull(fields?.numero_de_voie, fields?.nom_de_voie).joinToString(" ")


    override fun getTitle(): String? = fields?.horaires_ouverture



    override fun getPosition(): LatLng =
        LatLng(geometry.coordinates.last(), geometry.coordinates.first())
}