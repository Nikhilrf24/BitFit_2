package com.example.bitfit_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    lateinit var averageHoursTextView: TextView
    lateinit var maxHoursTextView: TextView
    lateinit var minHoursTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment, container, false)
        averageHoursTextView = view.findViewById(R.id.average_hours)
        maxHoursTextView = view.findViewById(R.id.max_hours)
        minHoursTextView = view.findViewById(R.id.min_hours)
        var min = Int.MAX_VALUE
        var max = 0
        var sum = 0
        lifecycleScope.launch {
            ((activity as MainActivity).application as BitfitApplication).db.sleepDao().getAll().collect {
                for (entity in it) {
                    val hoursString = entity.hours.toString()
                    val hoursNum = hoursString.toInt()
                    if (hoursNum < min) {
                        minHoursTextView.text = hoursString
                        min = hoursNum
                    }
                    if (hoursNum > max) {
                        maxHoursTextView.text = hoursString
                        max = hoursNum
                    }
                    sum += hoursNum
                }
                averageHoursTextView.text = (sum / it.size).toString()
            }
        }
        return view
    }

    companion object {
        fun newInstance() : DashboardFragment {
            return DashboardFragment()
        }
    }
}