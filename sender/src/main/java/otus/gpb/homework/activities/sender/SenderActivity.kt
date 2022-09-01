package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender_activity)
        findViewById<Button>(R.id.btn_gotoGoogleMaps).setOnClickListener {
            openGMaps("рестораны")
        }
        findViewById<Button>(R.id.btn_sendEmail).setOnClickListener {
            val theme = getString(R.string.forEmailTheme)
            val body = getString(R.string.forEmailBody).trimMargin()
            sendtoEmail("android@otus.ru", theme, body)
        }
    }

    private fun openGMaps(value: String) {
        val geoUriRestourantsString = "geo:0,0?z=10&q=$value"
        val geoUriR: Uri = Uri.parse(geoUriRestourantsString)
        val mapIntent = Intent(Intent.ACTION_VIEW, geoUriR)
        mapIntent.setPackage("com.google.android.apps.maps")
        mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(mapIntent)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Oops! Your phone has no GoogleMaps!\nAdd this app and try again!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun sendtoEmail(mail: String, theme: String, body: String) {
        val emailSend = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto: $mail")
            putExtra(Intent.EXTRA_SUBJECT, theme)
            putExtra(Intent.EXTRA_TEXT, body.toString())
        }
        try {
            startActivity(Intent.createChooser(emailSend, "Как будем отправлять?"))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                this, "Oops! Your phone has no Email Client!\n" +
                        "Add this app and try again!", Toast.LENGTH_SHORT
            )
                .show()
        }
    }
}

