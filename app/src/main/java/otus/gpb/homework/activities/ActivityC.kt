package otus.gpb.homework.activities

import android.content.Intent
import android.content.Intent.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

private const val TAG = "ActivityC"



class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        findViewById<Button>(R.id.button_to_A).setOnClickListener {
            startActivity(Intent(this, ActivityA::class.java))
        }

        findViewById<Button>(R.id.button_to_D).setOnClickListener {
            startActivity(Intent(this, ActivityD::class.java)
                .setFlags(FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK))
        }

        findViewById<Button>(R.id.button_close_C).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.button_close_Stack).setOnClickListener {
            finishAffinity()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent: $this")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: $this")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: $this")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: $this")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: $this")
    }
}