package com.stepgo.android.stepgo.presentation.views

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.stepgo.android.stepgo.R

class KtMainActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var navigationView: NavigationView
    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        fragmentManager = supportFragmentManager

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.container, MainScreenFragment(), null)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val fragmentTransaction = fragmentManager.beginTransaction()
        when(menuItem.itemId) {
            R.id.nav_walking -> fragmentTransaction.replace(R.id.container, MainScreenFragment(), null)
            R.id.nav_statistic -> fragmentTransaction.replace(R.id.container, StatisticFragment(), null)
        }
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}