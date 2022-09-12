package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityB : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)
        val buttonOpenC = findViewById<Button>(R.id.buttonOpenActivityC)
        buttonOpenC.setOnClickListener {
            startActivity(Intent(this, ActivityC::class.java))
        }
    }
}