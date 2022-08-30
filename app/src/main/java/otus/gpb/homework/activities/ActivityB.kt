package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class ActivityB : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)
        Log.d(TAG, "voj onCreate")
        findViewById<Button>(R.id.buttonOpenActivityC).setOnClickListener {
            val intent = Intent(this, ActivityC::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "voj onNewIntent")
    }

    private val TAG = "Activity B"

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "voj onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "voj onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "voj onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "voj onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "voj onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "voj onDestroy")
    }
}