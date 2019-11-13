package com.example.testside.util

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import android.content.Context
import com.google.android.gms.maps.model.MarkerOptions
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.testside.R
import com.example.testside.model.Record
import com.google.maps.android.ui.IconGenerator
import com.google.maps.android.clustering.ClusterManager
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.view.DefaultClusterRenderer


class MarkerClusterRenderer(
    context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<Record>
) : DefaultClusterRenderer<Record>(context, map, clusterManager) {
    private val iconGenerator: IconGenerator = IconGenerator(context)
    private val markerImageView: ImageView = ImageView(context)
    private val mContext = context

    init {
        markerImageView.layoutParams = ViewGroup.LayoutParams(MARKER_DIMENSION, MARKER_DIMENSION)
        iconGenerator.setContentView(markerImageView)
    }

    override fun onBeforeClusterItemRendered(item: Record, markerOptions: MarkerOptions) {
        markerImageView.setImageResource(R.drawable.ic_paper_roll)
        markerImageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.errorColor))
        val icon = iconGenerator.makeIcon()
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
        markerOptions.title(item.title)
    }

    companion object {
        private val MARKER_DIMENSION = 48
    }
}