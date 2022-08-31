package otus.gpb.homework.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

private const val TAG = "ActivityA"

class ActivityA : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)

        findViewById<Button>(R.id.button_open_ActivityB).setOnClickListener {
            Log.d(TAG, "press button open Activity B")
        }
    }
}