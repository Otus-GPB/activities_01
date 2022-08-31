package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.buttonToGoogleMaps).setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=restaurants")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                .setPackage("com.google.android.apps.maps")
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try {
                startActivity(mapIntent)
            } catch (e: ActivityNotFoundException) {
                Toast
                    .makeText(this, "Google Maps not found.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        findViewById<Button>(R.id.buttonSendEmail).setOnClickListener {
            val emailRecipient = "android@otus.ru"
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SENDTO
                data = Uri.parse("mailto:$emailRecipient")
                putExtra(Intent.EXTRA_EMAIL, emailRecipient)
                putExtra(Intent.EXTRA_SUBJECT, "Homework Activities_02")
                putExtra(Intent.EXTRA_TEXT, "Hello, Otus!")
            }
            try {
                startActivity(Intent.createChooser(sendIntent, "Choose email client:"))
            } catch (e: ActivityNotFoundException) {
                Toast
                    .makeText(this, "You can't send an email", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        findViewById<Button>(R.id.buttonOpenReceiver).setOnClickListener {
        }
    }
}