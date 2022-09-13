package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.button_to_google_maps).setOnClickListener {
            Log.d(TAG, "press button To Google map")
            intentToGoogleMap()
        }
        findViewById<Button>(R.id.button_send_emil).setOnClickListener {
            Log.d(TAG, "press Button Send email")
            composeEmail(addresses = arrayOf("android@otus.ru"), subject = "message from sender app")
        }
        findViewById<Button>(R.id.button_open_receiver).setOnClickListener {
            Log.d(TAG, "press Button Open receiver")
            intentOpenReceiver()
        }
    }

    private fun intentToGoogleMap() {
        val gmmIntentUri = Uri.parse("geo:0,0?q=restaurants")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    private fun composeEmail(addresses: Array<String>, subject: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        startActivity(intent)
    }

    private fun intentOpenReceiver() {
        val title = "Славные парни"
        val year = "2016"
        val description = "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."
        val intent = Intent(Intent.ACTION_SEND).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            putExtra(TITLE, title)
            putExtra(YEAR, year)
            putExtra(DESCRIPTION,description)
            type = "text/plain"
        }
        startActivity(intent)
    }


    companion object {
        private const val TITLE = "title"
        private const val YEAR = "year"
        private const val DESCRIPTION = "description"
        private const val TAG = "SenderActivity"
    }
}