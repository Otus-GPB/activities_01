package otus.gpb.homework.activities.sender

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.buttonToGoogleMaps).setOnClickListener {
        }
        findViewById<Button>(R.id.buttonSendEmail).setOnClickListener {
        }
        findViewById<Button>(R.id.buttonOpenReceiver).setOnClickListener {
        }
    }
}