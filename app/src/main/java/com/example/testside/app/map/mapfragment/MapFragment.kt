package com.example.testside.app.map.mapfragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.testside.R
import com.example.testside.adapter.CustomInfoViewAdapter
import com.example.testside.app.map.MapActivity
import com.example.testside.architecture.BaseFragment
import com.example.testside.model.Record
import com.example.testside.util.MarkerClusterRenderer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import es.dmoral.toasty.Toasty

class MapFragment : BaseFragment<MapFragmentPresenter>(), MapFragmentView {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private var mClusterManager: ClusterManager<Record>? = null
    private var googleMap: GoogleMap? = null
    private val PREFS_FILENAME = "STATE_KEY_MAP_CAMERA"
    private val LAT = "LAT"
    private val LGN = "LGN"
    private val ZOOM = "ZOOM"
    private val mIleDeFranceLatLng = LatLng(48.864716, 2.349014)
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private lateinit var lastLocation: Location


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_map, container, false)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.frg_map_container) as SupportMapFragment?
        mapFragment?.getMapAsync { mMap ->

            googleMap = mMap
            initMap(mMap)
            mClusterManager = ClusterManager(context, mMap)
            context?.let { setUpClusterManager(mClusterManager!!, mMap) }
            context?.let { mPresenter.getToilets() }
            mClusterManager?.markerCollection?.setOnInfoWindowAdapter(
                CustomInfoViewAdapter(
                    LayoutInflater.from(context)
                )
            )
        }
        fusedLocationClient = context?.let { LocationServices.getFusedLocationProviderClient(it) }

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

    override fun onStop() {
        super.onStop()
        Log.i("save Instance", "StOP")
        googleMap?.cameraPosition?.target?.latitude?.toString()?.let {
            saveCameraPositionState(
                LAT,
                it
            )
        }
        googleMap?.cameraPosition?.target?.longitude?.toString()?.let {
            saveCameraPositionState(
                LGN,
                it
            )
        }
        saveCameraPositionState(ZOOM, googleMap?.cameraPosition?.zoom.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        deleteMapState()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION) {
                googleMap?.let { initMap(it) }
            }
        }
    }

    fun setUpClusterManager(
        clusterManager: ClusterManager<Record>,
        googleMap: GoogleMap
    ) {
        mClusterManager = clusterManager
        clusterManager.renderer = context?.let {
            MarkerClusterRenderer(
                it,
                googleMap,
                clusterManager
            )
        }

        googleMap.setOnInfoWindowClickListener(mClusterManager);
        googleMap.setInfoWindowAdapter(clusterManager.getMarkerManager())
        googleMap.setOnCameraIdleListener(clusterManager)
    }

    fun initMap(map: GoogleMap) {
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.clear()
        if(Build.VERSION.SDK_INT >= 23 && checkPermission()) {
            map.isMyLocationEnabled = true
            fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->

                var cameraPosition: CameraPosition?
                if (getLastCamerPositionState(LAT) == null) {
                    if (location != null) {
                        lastLocation = location
                        val currentLatLng = LatLng(location.latitude, location.longitude)
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                    } else {
                        cameraPosition = CameraPosition.builder()
                            .target(mIleDeFranceLatLng)
                            .zoom(10f)
                            .bearing(0f)
                            .tilt(45f)
                            .build()
                        map.animateCamera(
                            CameraUpdateFactory.newCameraPosition(cameraPosition),
                            10000,
                            null
                        )
                    }

                } else {
                    cameraPosition = CameraPosition.builder()
                        .target(
                            LatLng(
                                getLastCamerPositionState(LAT)?.toDouble()!!,
                                getLastCamerPositionState(LGN)?.toDouble()!!
                            )
                        )
                        .zoom(getLastCamerPositionState(ZOOM)!!.toFloat())
                        .build()
                    map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                    deleteMapState()
                }

            }
        }
    }

    private fun saveCameraPositionState(tag: String, position: String) {
        context?.getSharedPreferences(PREFS_FILENAME, 0)?.edit()?.putString(tag, position)?.apply()
    }

    private fun getLastCamerPositionState(tag: String): String? =
        context?.getSharedPreferences(PREFS_FILENAME, 0)?.getString(tag, null)

    private fun deleteMapState() {
        context?.getSharedPreferences(PREFS_FILENAME, 0)?.edit()?.clear()?.commit()

    }

    private fun checkPermission() : Boolean {
        return if (context?.let { ContextCompat.checkSelfPermission(it, android.Manifest.permission.ACCESS_FINE_LOCATION) } == PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            requestPermissions()
            false
        }
    }

    private fun requestPermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), LOCATION_PERMISSION_REQUEST_CODE
        )
    }
}
