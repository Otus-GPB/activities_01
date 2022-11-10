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
            startActivity(Intent(this, ActivityA::class.java))
        }
        findViewById<Button>(R.id.btnOpenD).setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnCloseC).setOnClickListener {
            finish()
        }
        findViewById<Button>(R.id.btnCloseStack).setOnClickListener {
            finishAffinity()
        }
    }
}