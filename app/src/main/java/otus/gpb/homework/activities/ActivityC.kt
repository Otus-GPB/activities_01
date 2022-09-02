package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        findViewById<Button>(R.id.buttonC_A).setOnClickListener {
            intent = Intent(this, ActivityA::class.java)
            startActivity(intent)

        }

        findViewById<Button>(R.id.buttonD).setOnClickListener {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(Intent(this, ActivityD::class.java))
        }

        findViewById<Button>(R.id.buttonClose_C).setOnClickListener{
            finish()
        }

        findViewById<Button>(R.id.buttonClose_All).setOnClickListener {
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(Intent(this, ActivityA::class.java))
        }
    }
}