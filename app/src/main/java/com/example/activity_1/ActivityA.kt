package com.example.activity_1

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityA : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)

        findViewById<Button>(R.id.open_activity_b_button).setOnClickListener {
            val intent = Intent(this, ActivityB::class.java)
            startActivity(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }
}