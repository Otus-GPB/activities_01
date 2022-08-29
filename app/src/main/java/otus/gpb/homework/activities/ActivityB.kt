package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class ActivityB : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)
        supportActionBar?.title = "B"

        findViewById<Button>(R.id.action_open_activity_c)
            .setOnClickListener { openActivityC() }
    }

    private fun openActivityC(){
        intent = Intent(this, ActivityC::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w(localClassName, "ON_DESTROY B")
    }
}