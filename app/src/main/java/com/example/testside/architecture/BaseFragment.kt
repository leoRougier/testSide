package com.example.testside.architecture

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.android.support.DaggerFragment
import javax.inject.Inject


abstract class BaseFragment<P : BasePresenter<*>> : DaggerFragment(), BaseView {

    @Inject
    lateinit var mPresenter: P


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.onViewAttach(this)
        lifecycle.addObserver(mPresenter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycle.removeObserver(mPresenter)
    }
}