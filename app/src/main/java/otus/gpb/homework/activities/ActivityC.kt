package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)
        findViewById<Button>(R.id.btnOpenA).setOnClickListener {
            startActivity(Intent(this, ActivityA::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        }
        findViewById<Button>(R.id.btnOpenD).setOnClickListener {
            startActivity(Intent(this, ActivityD::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
        }
        findViewById<Button>(R.id.btnCloseC).setOnClickListener {
            finish()
        }
        findViewById<Button>(R.id.btnCloseStack).setOnClickListener {
            startActivity(Intent(this, ActivityA::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
        }
    }
}