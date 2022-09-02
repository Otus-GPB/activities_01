package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.collections.HashMap


class SenderActivity : AppCompatActivity() {
    lateinit var pay: Payload
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender_activity)
        pay = Payload(
            "Славные парни", "2016",
            "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы.\n"
        )
        val hash = HashMap<String, String>()
        hash.apply {
            put(KEY_PAYT, pay.title)
            put(KEY_PAYY, pay.year)
            put(KEY_PAYA, pay.description)
        }
        findViewById<Button>(R.id.btn_gotoGoogleMaps).setOnClickListener {
            openGMaps("рестораны")
        }
        findViewById<Button>(R.id.btn_sendEmail).setOnClickListener {
            val theme = getString(R.string.forEmailTheme)
            val body = getString(R.string.forEmailBody).trimMargin()
            sendtoEmail("android@otus.ru", theme, body)
        }
        findViewById<Button>(R.id.btn_openReceiver).setOnClickListener {
            openReceiver(hash)
        }
    }

    private fun openGMaps(value: String) {
        val geoUriRestourantsString = "geo:0,0?z=10&q=$value"
        val geoUriR: Uri = Uri.parse(geoUriRestourantsString)
        val googleIntent = Intent(Intent.ACTION_VIEW, geoUriR)
        googleIntent.setPackage("com.google.android.apps.maps")
        googleIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(googleIntent)
        } catch (e: Exception) {
            Toast
                .makeText(this,
                "Oops! Your phone has no GoogleMaps!" +
                        "\nAdd this app and try again!", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun sendtoEmail(mail: String, theme: String, body: String) {
        val emailSend = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto: $mail")
            putExtra(Intent.EXTRA_SUBJECT, theme)
            putExtra(Intent.EXTRA_TEXT, body)
            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        try {
            startActivity(Intent.createChooser(emailSend, "Как будем отправлять?"))
        } catch (e: ActivityNotFoundException) {
            Toast
                .makeText(
                this,
                    "Oops! Your phone has no Email Client!\n" +
                        "Add this app and try again!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun openReceiver(hash: HashMap<String, String>) {

        val payloadIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, hash)
            setType("text/plain")
        }
        try {
            startActivity(payloadIntent)
        } catch (e: Exception) {
            Toast
                .makeText(
                this,
                    "Oops! Your phone has no any Text Client!\n" +
                        "Add this app and try again!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        private const val KEY_PAYT = "Title"
        private const val KEY_PAYY = "Year"
        private const val KEY_PAYA = "About"
    }
}