package otus.gpb.homework.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

private const val TAG = "ActivityC"

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        findViewById<Button>(R.id.button_open_ActivityA).setOnClickListener {
            Log.d(TAG, "press button open Activity A")
        }
        findViewById<Button>(R.id.button_open_ActivityD).setOnClickListener {
            Log.d(TAG, "press button open Activity D")
        }
        findViewById<Button>(R.id.button_close_ActivityC).setOnClickListener {
            Log.d(TAG, "press button close Activity C")
        }
        findViewById<Button>(R.id.button_close_stack).setOnClickListener {
            Log.d(TAG, "press button close stack")
        }
    }
}