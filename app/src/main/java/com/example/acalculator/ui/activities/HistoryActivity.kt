package com.example.acalculator.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.acalculator.ui.utils.NavigationManager
import com.example.acalculator.R

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        NavigationManager.goToHistoryFragment(
            supportFragmentManager
        )
    }
}
