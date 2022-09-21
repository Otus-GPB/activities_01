package ru.desh.activities2.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import ru.desh.domain.Payload
import kotlin.random.Random

const val ACTION_SEND_MOVIE = "ru.desh.activity2.sender.SEND_MOVIE"
class SenderActivity : AppCompatActivity() {
    companion object{
        const val TAG = "SenderActivity"

        private const val SEND_TO = "android@otus.ru"
        private const val EMAIL_URI_SUBJECT_PARAM = "subject"
        private const val SUBJECT = "Test Subject"
        private const val EMAIL_URI_BODY_PARAM = "body"
        private const val BODY = "Hello!\n\n%s\n\nYours sincerely,\nApp Team"

        private const val GOOGLE_MAPS_PACKAGE = "com.google.android.apps.maps"
        private const val GOOGLE_MAPS_QUERY = "geo:0,0?q=restaurants"

        private const val EXTRA_PAYLOAD = "payload"

    }

    private val payload1 = Payload(
        title = "Славные парни",
        year = "2016",
        description = "Что бывает, когда напарником брутального костолома становится субтильный " +
                "лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены " +
                "работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое " +
                "оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если " +
                "у каждого из них – свои, весьма индивидуальные методы.\n"
    )
    private val payload2 = Payload(
        title = "Интерстеллар",
        year = "2014",
        description = "Когда засуха, пыльные бури и вымирание растений приводят человечество к " +
                "продовольственному кризису, коллектив исследователей и учёных отправляется сквозь " +
                "червоточину (которая предположительно соединяет области пространства-времени " +
                "через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для " +
                "космических путешествий человека и найти планету с подходящими для человечества " +
                "условиями."

    )

    private val onClickToGoogleMaps = View.OnClickListener {
        val restaurantsNearbyUri = Uri.parse(GOOGLE_MAPS_QUERY)
        val i = Intent(Intent.ACTION_VIEW, restaurantsNearbyUri)
        i.setPackage(GOOGLE_MAPS_PACKAGE)
        try {
            startActivity(i)
        } catch (e: ActivityNotFoundException){
            Toast.makeText(this, "Could not find Google Maps", Toast.LENGTH_LONG)
                .show()
        }
    }

    private val onClickSendEmail = View.OnClickListener {
        val i = Intent(Intent.ACTION_SENDTO)

        // # 1
        // Единственный вариант, при котором корректно обрабатываются все данные
        i.data = Uri.parse("mailto:$SEND_TO" +
                "?$EMAIL_URI_SUBJECT_PARAM=$SUBJECT" +
                "&$EMAIL_URI_BODY_PARAM=${BODY.format("Some Message...")}")

        // # 2
        //    .buildUpon()
        //    .appendQueryParameter("subject", SUBJECT)
        //    .appendQueryParameter("body", BODY.format("Some Message..."))
        //    .build()

        // # 3
        //i.putExtra(Intent.EXTRA_EMAIL, SEND_TO)
        //i.putExtra(Intent.EXTRA_SUBJECT, SUBJECT)
        //i.putExtra(Intent.EXTRA_TEXT, BODY.format("Some Message..."))
        try {
            startActivity(i)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Could not find Email client", Toast.LENGTH_LONG)
                .show()
        }
    }

    private val onClickOpenReceiver = View.OnClickListener {
        val i = Intent(ACTION_SEND_MOVIE)
        i.type = "text/plain"
        i.addCategory(Intent.CATEGORY_DEFAULT)

        // Передача случайного фильма в payload
        val rand = Random(System.currentTimeMillis())
        (rand.nextInt() % 2).apply {
            i.putExtra(EXTRA_PAYLOAD, if (this == 1)  payload1 else payload2)
        }
        try {
            startActivity(i)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Could not find Receiver", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.button_to_google_maps).setOnClickListener(onClickToGoogleMaps)
        findViewById<Button>(R.id.button_send_email).setOnClickListener(onClickSendEmail)
        findViewById<Button>(R.id.button_open_receiver).setOnClickListener(onClickOpenReceiver)
    }
}