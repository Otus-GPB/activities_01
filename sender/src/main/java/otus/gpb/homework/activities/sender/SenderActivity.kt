package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.*

private const val MAIL_TO = "android@otus.ru"
private const val MAIL_SUBJ = "Test intent"

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.gmaps_btn).setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Рестораны")).setPackage(
                    "com.google.android.apps.maps"
                )
            )
        }

        findViewById<Button>(R.id.sendmail_btn).setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_SENDTO)
                    .setData(Uri.parse("mailto:$MAIL_TO"))
                    .putExtra(Intent.EXTRA_SUBJECT, MAIL_SUBJ)
            )
        }

        findViewById<Button>(R.id.open_receiver_btn).setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_SEND)
                    .addCategory(Intent.CATEGORY_DEFAULT)
                    .putExtra("title", "Славные парни")
                    .putExtra("year", "2016")
                    .putExtra("description", "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы.")
                    .setType("text/plain")
            )
        }
    }
}