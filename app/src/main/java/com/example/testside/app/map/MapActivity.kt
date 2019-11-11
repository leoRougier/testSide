package com.example.testside.app.map

import android.os.Bundle
import android.util.Log
import com.example.testside.R
import com.example.testside.app.map.mapfragment.MapFragment
import com.example.testside.architecture.BaseActivity

class MapActivity: BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        Log.i("Start", "start")
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        ft.replace(R.id.map_fl_container, MapFragment(), "map").commit()
    }
}