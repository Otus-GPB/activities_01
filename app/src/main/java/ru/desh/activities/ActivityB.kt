package ru.desh.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class ActivityB : AppCompatActivity() {
    companion object {
        private val TAG = "ActivityB"
    }

    private val onClickStartC = View.OnClickListener {
        val i = Intent(this, ActivityC::class.java)
        startActivity(i)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)
        findViewById<Button>(R.id.button_open_c).setOnClickListener(onClickStartC)
    }

}