package com.example.bitfit_2

import android.app.Application

class BitfitApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}