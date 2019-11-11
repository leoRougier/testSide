package com.example.testside.architecture

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* mPresenter.onViewAttach(this)
        lifecycle.addObserver(mPresenter)*/
    }

    override fun onDestroy() {
        super.onDestroy()
       // lifecycle.removeObserver(mPresenter)
    }

}