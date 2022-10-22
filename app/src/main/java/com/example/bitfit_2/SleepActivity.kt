package com.example.bitfit_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SleepActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sleep_activity)

        findViewById<Button>(R.id.record2).setOnClickListener {
            val hours = findViewById<EditText>(R.id.hours)
            val date = findViewById<EditText>(R.id.sleepdate)

            val sleepItem = SleepItem(hours.text.toString(), date.text.toString())

            hours.setText("")
            date.setText("")

            lifecycleScope.launch(Dispatchers.IO) {
                (application as BitfitApplication).db.sleepDao().insert(
                    SleepEntity(
                        hours = sleepItem.hours,
                        date = sleepItem.date
                    )
                )
            }
            finish()
        }
    }
}