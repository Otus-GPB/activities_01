package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class SenderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.open_google_map_btn).setOnClickListener {
            val mapUriIntent = Uri.parse("geo:0,0?q=рестораны")
            val intent = Intent(Intent.ACTION_VIEW, mapUriIntent)
            startActivity(intent)
        }

        findViewById<Button>(R.id.send_email_btn).setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
                putExtra(Intent.EXTRA_SUBJECT, "Exams")
                putExtra(Intent.EXTRA_TEXT, "На какие даты назначены консультации по экзаменам?")
            }
            startActivity(Intent.createChooser(emailIntent, "Send Email"))
        }

        findViewById<Button>(R.id.open_receiver_btn).setOnClickListener {
            val aMovie = Movie(
                "Славные парни",
                "2016",
                "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."
            )
            val bMovie = Movie(
                "Интерстеллар",
                "2014",
                "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями."
            )
            val extras = createExtra(aMovie, bMovie)

            val intent = Intent(Intent.ACTION_SEND)
                .addCategory(Intent.CATEGORY_DEFAULT)
                .putExtra(EXTRA_TITLE, extras.title)
                .putExtra(EXTRA_YEAR, extras.year)
                .putExtra(EXTRA_DESCRIPTION, extras.description)
                .setType("text/plain")
            startActivity(intent)
        }

    }

    private fun createExtra(a: Movie, b: Movie) : Movie {
        val random = Random.nextInt(1, 3)
        val extras: Movie = if (random == 1) {
            a
        } else b
        return extras
    }

    companion object {

        const val EXTRA_TITLE = "title"
        const val EXTRA_YEAR = "year"
        const val EXTRA_DESCRIPTION = "description"

        data class Movie(
            val title: String,
            val year: String,
            val description: String
        )
    }
}