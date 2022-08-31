package otus.gpb.homework.activities.sender

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)


        findViewById<Button>(R.id.open_google_map).setOnClickListener {

        }

        findViewById<Button>(R.id.send_email).setOnClickListener {

        }

        findViewById<Button>(R.id.open_receiver).setOnClickListener {

        }
    }
}