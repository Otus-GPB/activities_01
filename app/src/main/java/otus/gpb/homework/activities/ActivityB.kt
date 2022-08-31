package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

private const val TAG = "ActivityB"

class ActivityB : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)
        Log.d(TAG, "onCreate: $this")

        findViewById<Button>(R.id.button_open_ActivityC).setOnClickListener {
            Log.d(TAG, "press button open Activity C")
            val intent = Intent(this, ActivityC::class.java)
            startActivity(intent)
        }
    }
}