package com.example.testside.app.map.mapfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.testside.R
import com.example.testside.adapter.CustomInfoViewAdapter
import com.example.testside.architecture.BaseFragment
import com.example.testside.model.Record
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.maps.android.clustering.ClusterManager
import es.dmoral.toasty.Toasty

class MapFragment : BaseFragment<MapFragmentPresenter>(), MapFragmentView {
    private var mClusterManager: ClusterManager<Record>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.frg_map_container) as SupportMapFragment?
        mapFragment?.getMapAsync { mMap ->

            mPresenter.initMap(mMap)
            mClusterManager = ClusterManager(context, mMap)
            context?.let { setUpClusterManager(mClusterManager!!, mMap) }
            context?.let { mPresenter.getToilets() }
            mClusterManager?.markerCollection?.setOnInfoWindowAdapter(CustomInfoViewAdapter(LayoutInflater.from(context)))
        }

        return rootView
    }

    override fun error() {
        activity?.applicationContext?.let {
            Toasty.error(it, getString(R.string.map_error), Toast.LENGTH_SHORT, true).show()
        }
    }

    override fun clusterAddItem(records: List<Record>?) {
        mClusterManager?.addItems(records)
        mClusterManager?.cluster()
    }

    fun setUpClusterManager(
        clusterManager: ClusterManager<Record>,
        googleMap: GoogleMap
    ) {
        mClusterManager = clusterManager
        googleMap.setOnInfoWindowClickListener(mClusterManager);
        googleMap.setInfoWindowAdapter(clusterManager.getMarkerManager())
        googleMap.setOnCameraIdleListener(clusterManager)
    }
}


/* private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
     val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
     vectorDrawable!!.setBounds(
         0,
         0,
         vectorDrawable.intrinsicWidth,
         vectorDrawable.intrinsicHeight
     )
     val bitmap = Bitmap.createBitmap(
         vectorDrawable.intrinsicWidth,
         vectorDrawable.intrinsicHeight,
         Bitmap.Config.ARGB_8888
     )
     val canvas = Canvas(bitmap)
     vectorDrawable.draw(canvas)
     return BitmapDescriptorFactory.fromBitmap(bitmap)
 }*/
