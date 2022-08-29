package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)
        supportActionBar?.title = "C"

        findViewById<Button>(R.id.action_open_activity_a)
            .setOnClickListener { openActivityA() }
        findViewById<Button>(R.id.action_close_activity_c)
            .setOnClickListener { closeActivityC() }
        findViewById<Button>(R.id.action_open_activity_d)
            .setOnClickListener { openActivityD() }
        findViewById<Button>(R.id.action_close_stack)
            .setOnClickListener { closeStack() }
    }

    private fun openActivityA(){
        intent = Intent(this, ActivityA::class.java)
        startActivity(intent)
    }

    private fun openActivityD(){
        intent = Intent(this, ActivityD::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    private fun closeActivityC(){
        finish()
    }

    private fun closeStack(){
        intent = Intent(this, ActivityA::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w(localClassName, "ON_DESTROY C")
    }
}