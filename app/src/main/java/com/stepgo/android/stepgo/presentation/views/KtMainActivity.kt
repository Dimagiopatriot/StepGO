package com.stepgo.android.stepgo.presentation.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.stepgo.android.stepgo.R

class KtMainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.container, MainScreenFragment(), null)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}