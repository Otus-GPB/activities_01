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
        val btnGoogleMaps = findViewById<Button>(R.id.btn_google_maps)
        btnGoogleMaps.setOnClickListener {
            val uri = Uri.parse("geo:0,0?q=рестораны")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)

        }
        val btnSendEmail = findViewById<Button>(R.id.btn_send_email)
        btnSendEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
                putExtra(Intent.EXTRA_SUBJECT, "test subject")
                putExtra(Intent.EXTRA_TEXT, "test text")
            }
            startActivity(intent)
        }
        val btnOpenReceiver = findViewById<Button>(R.id.btn_open_receiver)
        btnOpenReceiver.setOnClickListener {  }
    }
}