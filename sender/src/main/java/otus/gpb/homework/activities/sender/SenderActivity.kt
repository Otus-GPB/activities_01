package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.util.stream.Collectors
import kotlin.random.Random

class SenderActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.to_google_maps_button).setOnClickListener {
            val uri = Uri.parse("geo:0.0,0.0?q=restaurants")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        findViewById<Button>(R.id.send_email_button).setOnClickListener {

            val intentEmail = Intent(Intent.ACTION_SENDTO)
            intentEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))

            intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Message topic")
            intentEmail.putExtra(Intent.EXTRA_TEXT, "Hello, OTUS!")

            intentEmail.data = Uri.parse("mailto:")
            startActivity(Intent.createChooser(intentEmail, "Choose an email client from..."))
        }

        findViewById<Button>(R.id.open_receiver_button).setOnClickListener {
            val abc = resources.assets.open("payload.txt")
                .bufferedReader()
                .lines()
                .filter {s -> s.isNotEmpty()}
                .collect(Collectors.toList())

            val randBoolean = Random.Default.nextBoolean()
            val randInt = if (randBoolean) 0 else 3
            val payload = Payload(
                abc[randInt], abc[randInt + 1], abc[randInt + 2]
            )

            val intent = Intent(Intent.ACTION_SEND)
                .addCategory(Intent.CATEGORY_DEFAULT)
                .putExtra("EXTRA_TITLE", payload.title)
                .putExtra("EXTRA_YEAR", payload.year)
                .putExtra("EXTRA_DESCRIPTION", payload.description)
                .setType("text/plain")
            startActivity(intent)
        }
    }
}