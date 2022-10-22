package com.example.bitfit_2

import kotlinx.serialization.Serializable

@Serializable
class SleepItem(
    val hours : String?,
    val date : String?) : java.io.Serializable {
}