package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)
        findViewById<Button>(R.id.buttonOpenActivityA).setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        findViewById<Button>(R.id.buttonOpenActivityD).setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        findViewById<Button>(R.id.buttonCloseActivityC).setOnClickListener {
            finish()
        }
        findViewById<Button>(R.id.buttonCloseStack).setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finishAffinity()
            startActivity(intent)
        }
    }
}