package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.buttonToGoogleMaps).setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=restaurants")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                .setPackage("com.google.android.apps.maps")
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(mapIntent)
        }
        findViewById<Button>(R.id.buttonSendEmail).setOnClickListener {
        }
        findViewById<Button>(R.id.buttonOpenReceiver).setOnClickListener {
        }
    }
}