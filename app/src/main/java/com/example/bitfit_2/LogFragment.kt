package com.example.bitfit_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class LogFragment : Fragment() {

    private lateinit var sleepLog : ArrayList<SleepItem>
    private lateinit var sleepRv : RecyclerView
    private lateinit var sleepAdapter: SleepAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_log, container, false)

        sleepRv = view.findViewById(R.id.sleepLogs)
        sleepRv.setHasFixedSize(true)
        sleepLog = ArrayList()
        sleepAdapter = SleepAdapter(sleepLog, activity as MainActivity)
        sleepRv.adapter = sleepAdapter

        lifecycleScope.launch {
            ((activity as MainActivity).application as BitfitApplication).db.sleepDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    SleepItem(
                        entity.hours,
                        entity.date
                    )
                }.also { mappedList ->
                    sleepLog.clear()
                    sleepLog.addAll(mappedList)
                    sleepAdapter.notifyDataSetChanged()
                }
            }
        }

        sleepRv.layoutManager = LinearLayoutManager(context).also {
            val dividerItemDecoration = DividerItemDecoration(context, it.orientation)
            sleepRv.addItemDecoration(dividerItemDecoration)
        }
        return view
    }

    companion object {
        fun newInstance(): LogFragment {
            return LogFragment()
        }
    }
}