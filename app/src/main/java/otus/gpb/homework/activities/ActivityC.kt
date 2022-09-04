package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        findViewById<Button>(R.id.open_a_button).setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)

        }

        findViewById<Button>(R.id.open_d_button).setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        findViewById<Button>(R.id.close_c_button).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.close_stack).setOnClickListener {
            finishAffinity()
        }

    }
}