package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SenderActivity : AppCompatActivity() {

    companion object {
        const val ARG_TITLE = "ARG_TITLE"
        const val ARG_YEAR = "ARG_YEAR"
        const val ARG_DESCRIPTION = "ARG_DESCRIPTION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val movies = listOf(
            Payload(
                "Интерстеллар",
                "2014",
                "Когда засуха, пыльные бури и вымирание растений приводят человечество к " +
                        "продовольственному кризису, коллектив исследователей и учёных отправляется сквозь " +
                        "червоточину (которая предположительно соединяет области пространства-времени через большое " +
                        "расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий " +
                        "человека и найти планету с подходящими для человечества условиями."
            ),
            Payload(
                "Славные парни",
                "2016",
                "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный " +
                        "охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы " +
                        "распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут " +
                        "ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."
            )
        )

        findViewById<Button>(R.id.button_send_email).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/html"
                putExtra(Intent.EXTRA_EMAIL, "android@otus.ru")
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.text_email_subject))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.text_email_message))
            }
            startActivity(Intent.createChooser(intent, getString(R.string.title_send_email)))
        }

        findViewById<Button>(R.id.button_google_maps).setOnClickListener {
            val intentUri = Uri.parse("geo:0,0?q=restaurants")
            val intent = Intent(Intent.ACTION_VIEW, intentUri).apply {
                setPackage("com.google.android.apps.maps")
            }
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_open_receiver).setOnClickListener {

            val intent = Intent(Intent.ACTION_SEND).apply {
                val movie = movies.random()
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)
                putExtra(ARG_TITLE, movie.title)
                putExtra(ARG_YEAR, movie.year)
                putExtra(ARG_DESCRIPTION, movie.description)
            }
            startActivity(intent)
        }
    }
}
