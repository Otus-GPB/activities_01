package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

private const val TAG = "SenderActivity"

class SenderActivity : AppCompatActivity() {
    val movies = listOf(
        Payload("Славные парни",
                "2016",
                "Что бывает, когда напарником брутального костолома становится субтильный лопух? " +
                        "Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены " +
                        "работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается " +
                        "преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – " +
                        "свои, весьма индивидуальные методы."),
        Payload("Интерстеллар",
                "2014",
                "Когда засуха, пыльные бури и вымирание растений приводят " +
                        "человечество к продовольственному кризису, коллектив исследователей и учёных " +
                        "отправляется сквозь червоточину (которая предположительно соединяет области " +
                        "пространства-времени через большое расстояние) в путешествие, чтобы превзойти " +
                        "прежние ограничения для космических путешествий человека и найти планету с " +
                        "подходящими для человечества условиями.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.btnGoogleMap).setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:56.327090902832666, 44.00565551878041?q=Рестораны")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        findViewById<Button>(R.id.btnSendEmail).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "ВЫ ВЫИГРАЛИ МИЛЛИОН")
            intent.putExtra(Intent.EXTRA_TEXT, "ОЙ, ЭТО БЫЛА ШУТКА")

            val chooseIntent = Intent.createChooser(intent, "Выберете почту")
            startActivity(chooseIntent)
        }

        findViewById<Button>(R.id.btnOpenReceiver).setOnClickListener {
            val movie = movies.random()

            Log.d(TAG, "RANDOM MOVIE ${movie.title}")

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.putExtra("title", movie.title)
            intent.putExtra("year", movie.year)
            intent.putExtra("description", movie.description)

            startActivity(intent)
        }

    }
}