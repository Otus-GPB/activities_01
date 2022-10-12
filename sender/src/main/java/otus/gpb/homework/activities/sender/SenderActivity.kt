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
        setContentView(R.layout.activity_sender)
        findViewById<Button>(R.id.button3).setOnClickListener {
            // Search for restaurants nearby
            val gmmIntentUri = Uri.parse("geo:0,0?q=restaurants")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")    
            startActivity(mapIntent)
        }


        findViewById<Button>(R.id.button4).setOnClickListener {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "message/rfc822"
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
            i.putExtra(Intent.EXTRA_SUBJECT, "Тема сообщения.")
            i.putExtra(Intent.EXTRA_TEXT, "Текст сообщения.")
            startActivity(i)
        }


        findViewById<Button>(R.id.button5).setOnClickListener {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.addCategory("android.intent.category.DEFAULT")
            i.putExtra("title",  "Интерстеллар")
            i.putExtra("year",  "2014")
            i.putExtra("description",  "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.")
            startActivity(i)
        }
    }
}