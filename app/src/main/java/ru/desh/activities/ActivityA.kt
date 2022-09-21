package ru.desh.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button


class ActivityA : AppCompatActivity() {
    companion object {
        private val TAG = "ActivityA"
    }

    private val onClickStartB = View.OnClickListener {
        val i = Intent(this, ActivityB::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i)
    }

    // Для отслеживания текущих задач и стека использовал
    // adb shell dumpsys activity activities | grep ru.desh.activities | grep Hist
    // adb shell dumpsys activity activities | grep ru.desh.activities | grep Task
    // https://stackoverflow.com/questions/2442713/view-the-tasks-activity-stack
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        findViewById<Button>(R.id.button_open_b).setOnClickListener(onClickStartB)
        Log.d(TAG, "onCreate $this")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent $this")
    }

}