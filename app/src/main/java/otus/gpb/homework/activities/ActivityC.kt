package otus.gpb.homework.activities

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
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
            val intent = Intent(this, ActivityA::class.java)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        findViewById<Button>(R.id.button_open_ActivityD).setOnClickListener {
            Log.d(TAG, "press button open Activity D")
            val intent = Intent(this, ActivityD::class.java)
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        findViewById<Button>(R.id.button_close_ActivityC).setOnClickListener {
            Log.d(TAG, "press button close Activity C")
            finish()
        }
        findViewById<Button>(R.id.button_close_stack).setOnClickListener {
            Log.d(TAG, "press button close stack")
//            val intent = Intent(this, ActivityA::class.java)
//            intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK)
//            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
            finishAffinity()
        }
    }
}