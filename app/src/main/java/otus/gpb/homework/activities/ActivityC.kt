package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        findViewById<Button>(R.id.act_c_button_a).setOnClickListener {
            startActivity(Intent(this, ActivityA::class.java))
        }

        findViewById<Button>(R.id.act_c_button_d).setOnClickListener {
            startActivity(Intent(this, ActivityD::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
        }

        findViewById<Button>(R.id.act_c_button_close).setOnClickListener {
            super.finish()
        }

        findViewById<Button>(R.id.act_c_button_close_stack).setOnClickListener {
            finishAffinity()
            startActivity(Intent(this,ActivityA::class.java))
        }
    }
}