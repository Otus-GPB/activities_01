package com.example.activity_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        findViewById<Button>(R.id.open_activity_a_button).setOnClickListener {
            val  intent = Intent(this, ActivityA::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.open_activity_d_button).setOnClickListener {
            val  intent = Intent(this, ActivityD::class.java)
            startActivity(intent)
            finishAffinity()
        }

        findViewById<Button>(R.id.close_activity_c_button).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.close_stack_button).setOnClickListener {
            finishAffinity()
        }
    }
}