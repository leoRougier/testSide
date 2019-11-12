package com.example.testside.app.map.listfragment

import com.example.testside.architecture.BaseView
import com.example.testside.model.Record

interface ListFragmentView:BaseView {
    fun error()
    fun showToilet(records: List<Record>?)
}