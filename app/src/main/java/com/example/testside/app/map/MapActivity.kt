package com.example.testside.app.map

import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.testside.R
import com.example.testside.app.map.listfragment.ListFragment
import com.example.testside.app.map.mapfragment.MapFragment
import com.example.testside.architecture.BaseActivity
import kotlinx.android.synthetic.main.activity_map.*
import java.security.AccessController.getContext

class MapActivity : BaseActivity() {

    private val tagMapFragment = "MAP_FRAGMENT"
    private val tagListFragment = "LIST_FRAGMENT"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        openFragment(MapFragment(), tagMapFragment)
        map_activity_fab.setOnClickListener {
            if (getVisibleFragment(tagMapFragment)) {
                openFragment(ListFragment(), tagListFragment)
                map_activity_fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_maps));

            }else{
                openFragment(MapFragment(), tagMapFragment)
                map_activity_fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_list));

            }
        }
    }

    private fun getVisibleFragment(tag: String): Boolean {
        return supportFragmentManager.findFragmentByTag(tag) != null && supportFragmentManager.findFragmentByTag(
            tag
        )!!.isVisible
    }

    private fun openFragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.map_fl_container, fragment, tag)
        transaction.addToBackStack(null)
        transaction.commit()

    }
}
