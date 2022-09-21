package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SenderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val buttonToGoogleMaps = findViewById<Button>(R.id.btnToGoogleMaps)
        buttonToGoogleMaps.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=restaurants")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        val buttonSendEmail = findViewById<Button>(R.id.btnSendEmail)
        buttonSendEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:") // only email apps should handle this
                putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
                putExtra(Intent.EXTRA_SUBJECT, "домашнее задание")
            }
            startActivity(intent)
        }
        val buttonOpenReceiver = findViewById<Button>(R.id.btnOpenReceiver)
        buttonOpenReceiver.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)
                putExtra("title", "Славные парни")
                putExtra( "year", "2016")
                putExtra("description", "Что бывает, когда напарником брутального костолома становится субтильный лопух? " +
                        "Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое " +
                        "дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если " +
                        "у каждого из них – свои, весьма индивидуальные методы.")
            }
            startActivity(intent)
        }
    }
}

