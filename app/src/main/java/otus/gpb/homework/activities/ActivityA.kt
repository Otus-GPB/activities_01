package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

private const val TAG = "ActivityA"

class ActivityA : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        Log.d(TAG, "onCreate: $this")

        findViewById<Button>(R.id.button_open_ActivityB).setOnClickListener {
            Log.d(TAG, "press button open Activity B")
            val intent = Intent(this, ActivityB::class.java)
            startActivity(intent)
        }
    }
}