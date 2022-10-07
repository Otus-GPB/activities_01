package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        findViewById<Button>(R.id.btn_open_activity_a).setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_open_activity_d).setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finishAffinity()
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_close_activity_c).setOnClickListener {
            this.finish()
        }

        findViewById<Button>(R.id.btn_close_stack).setOnClickListener {
            finishAffinity()
        }
    }
}