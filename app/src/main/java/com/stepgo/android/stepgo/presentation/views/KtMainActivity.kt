package com.stepgo.android.stepgo.presentation.views

import android.os.Bundle
import androidx.core.view.GravityCompat
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
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
        fragmentTransaction.commit()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val fragmentTransaction = fragmentManager.beginTransaction()
        when(menuItem.itemId) {
            R.id.nav_walking -> fragmentTransaction.replace(R.id.container, MainScreenFragment(), null)
            R.id.nav_statistic -> fragmentTransaction.replace(R.id.container, StatisticFragment(), null)
            R.id.nav_music -> fragmentTransaction.replace(R.id.container, MusicPlayerFragment(), null)
        }
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        val currentFragment = fragmentManager.findFragmentById(R.id.container)
        if (currentFragment is MusicPlayerFragment) {
            currentFragment.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}