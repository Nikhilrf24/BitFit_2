package com.example.bitfit_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logFragment : Fragment = LogFragment()
        val dashboardFragment : Fragment = DashboardFragment()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.nav_log -> fragment = logFragment
                R.id.nav_dashboard -> fragment = dashboardFragment
            }
            replaceFragment(fragment)
            true
        }

        bottomNavigationView.selectedItemId = R.id.nav_log

        findViewById<Button>(R.id.clear).setOnClickListener {
            lifecycleScope.launch(IO) {
                (application as BitfitApplication).db.sleepDao().deleteAll()
            }
        }

        findViewById<Button>(R.id.record).setOnClickListener {
            val intent = Intent(this, SleepActivity::class.java)
            startActivity(intent)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.basic_frame_layout, fragment)
        fragmentTransaction.commit()
    }

    fun delete(sleepItem : SleepItem) {
        lifecycleScope.launch(IO) {
            (application as BitfitApplication).db.sleepDao().delete(
                SleepEntity(
                    hours = sleepItem.hours,
                    date = sleepItem.date
                )
            )
        }
    }
}