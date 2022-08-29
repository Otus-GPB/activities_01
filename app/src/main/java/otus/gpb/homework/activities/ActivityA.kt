package otus.gpb.homework.activities

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ActivityA : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        supportActionBar?.title = "A"

        findViewById<Button>(R.id.action_open_activity_b)
            .setOnClickListener { openActivityB() }
    }

    private fun openActivityB(){
        intent = Intent(this, ActivityB::class.java)
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w(localClassName, "ON_DESTROY A")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.w(localClassName, "ON_NEW_INTENT")
    }
}