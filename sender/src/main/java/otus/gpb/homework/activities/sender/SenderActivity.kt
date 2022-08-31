package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.MailTo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

private const val EMAIL = "android@otus.ru"

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)


        findViewById<Button>(R.id.open_google_map).setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("geo:0.0.0?q=Рестораны")
                ).setPackage("com.google.android.apps.maps"))
        }

        findViewById<Button>(R.id.send_email).setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_SENDTO)
                    .setData(Uri.parse("mailto: $EMAIL"))
                    .putExtra(Intent.EXTRA_EMAIL, "HELLO WORLD!")
                    .putExtra(Intent.EXTRA_TEXT, "IM BLUE DABYDIDABYDAI")
            )
        }

        findViewById<Button>(R.id.open_receiver).setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_SEND)
                    .addCategory(Intent.CATEGORY_DEFAULT)
                    .putExtra("title", "Интерстеллар")
                    .putExtra("year", "2014")
                    .putExtra("description", "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.")
                    .setType("text/plain")
            )
        }
    }
}