package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityA : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        findViewById<Button>(R.id.buttonb).setOnClickListener {
            startActivity(Intent(this, ActivityB :: class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }


    }
}