package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ActivityA : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)

        val button = findViewById<Button>(R.id.act_a_button)
        button.setOnClickListener {
            startActivity(Intent(this, ActivityB::class.java))
        }
    }
}