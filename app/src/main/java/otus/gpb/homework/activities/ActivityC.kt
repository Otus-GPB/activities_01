package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityC : AppCompatActivity(R.layout.activity_c) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<Button>(R.id.buttonActivityA).setOnClickListener {
            startActivity(Intent(this, ActivityA::class.java))
        }

        findViewById<Button>(R.id.buttonActivityD).setOnClickListener {
            val intent = Intent(this, ActivityD::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        }

        findViewById<Button>(R.id.buttonCloseActivityC).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.buttonCloseStack).setOnClickListener {
            val intent = Intent(this, ActivityA::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
            finishAffinity()
        }
    }
}
