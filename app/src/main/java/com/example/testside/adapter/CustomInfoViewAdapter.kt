package com.example.testside.adapter

import android.view.LayoutInflater
import android.view.View
import com.example.testside.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.infos_window_layout.view.*

class CustomInfoViewAdapter(private val inflater: LayoutInflater): GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(marker: Marker?): View? {
        val info: View = inflater.inflate(R.layout.infos_window_layout, null)
        info.txt_adapter_street.text = marker?.title
        return null    }

    override fun getInfoWindow(marker: Marker?): View? {
        val info: View = inflater.inflate(R.layout.infos_window_layout, null)
        info.txt_adapter_street.text = marker?.title
        info.txt_adapter_time.text = marker?.snippet
        return info
    }
}