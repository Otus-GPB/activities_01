package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

private const val TITLE = "title"
private const val YEAR = "year"
private const val DESCRIPTION = "description"

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val movies = listOf(
            Payload(
                "Interstellar",
                "2014",
                "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями."
            ),
            Payload(
                "The Nice Guys",
                "2014",
                "Amelia, a girl in hiding, hires Healy to put March, a detective seeking her, in his place. After two hitmen try to get Healy to reveal her whereabouts, he concernedly pairs with March to find her."
            )
        )

        findViewById<Button>(R.id.google_maps).setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=restaurants")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        findViewById<Button>(R.id.send_email).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/html"
                putExtra(Intent.EXTRA_EMAIL, "android@otus.ru")
                putExtra(Intent.EXTRA_SUBJECT, "Subject")
                putExtra(Intent.EXTRA_TEXT, "I'm email body.")
            }
            startActivity(Intent.createChooser(intent, "Send Email"))
        }

        findViewById<Button>(R.id.open_receiver).setOnClickListener {

            val intent = Intent(Intent.ACTION_SEND).apply {
                val movie = movies.random()
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)
                putExtra(TITLE, movie.title)
                putExtra(YEAR, movie.year)
                putExtra(DESCRIPTION, movie.description)
            }
            startActivity(intent)
        }
    }
}