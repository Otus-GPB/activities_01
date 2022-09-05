package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

const val TAG = "SenderActivity"

class SenderActivity : AppCompatActivity() {
    val films = listOf(
        Payload(
            "Интерстеллар",
            "2014",
            "Когда засуха, пыльные бури и вымирание растений приводят человечество к " +
                    "продовольственному кризису, коллектив исследователей и учёных отправляется " +
                    "сквозь червоточину (которая предположительно соединяет области " +
                    "пространства-времени через большое расстояние) в путешествие, чтобы превзойти " +
                    "прежние ограничения для космических путешествий человека и найти планету с " +
                    "подходящими для человечества условиями."
        ),
        Payload(
            "Плохие парни",
            "2016",
            "Что бывает, когда напарником брутального костолома становится субтильный " +
                    "лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены " +
                    "работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое " +
                    "оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, " +
                    "если у каждого из них – свои, весьма индивидуальные методы."
        )
    )

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
        btnOpenReceiver.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)
                val film = films.random()
                putExtra("title", film.title)
                putExtra("year", film.year)
                putExtra("description", film.description)
            }
            startActivity(intent)

        }

    }

}