package com.example.testside.app.map.listfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.testside.R
import com.example.testside.adapter.ToiletListAdapter
import com.example.testside.architecture.BaseFragment
import com.example.testside.model.Record
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : BaseFragment<ListFragmentPresenter>(), ListFragmentView {


    private lateinit var mAdapter: ToiletListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPresenter.getCachedtoilet()
        fragment_list_swipeContainer.setOnRefreshListener {
            mAdapter.clear()
            mPresenter.getCachedtoilet()
            fragment_list_swipeContainer.isRefreshing = false
        }
    }

    override fun showToilet(records: List<Record>?) {
        mAdapter = ToiletListAdapter()
        fragment_list_rcv_toilets.layoutManager = LinearLayoutManager(context)
        fragment_list_rcv_toilets.adapter = mAdapter
        mAdapter.setItem(records)
    }

    override fun error() {
        activity?.applicationContext?.let {
            Toasty.error(it, getString(R.string.map_error), Toast.LENGTH_SHORT, true).show()
        }
    }
}