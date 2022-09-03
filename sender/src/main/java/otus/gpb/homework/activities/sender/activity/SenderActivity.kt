package otus.gpb.homework.activities.sender.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.sender.R

class SenderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sender_activity_layout)

        findViewById<Button>(R.id.google_map_btn).setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.google.ru/maps/search/Рестораны/")
            )
            startActivity(intent)
        }

        findViewById<Button>(R.id.send_mail_btn).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
                .setType("message/rfc822")
                .putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru", "i.s.rakhcheev@gmail.com"))
                .putExtra(Intent.EXTRA_SUBJECT, "Greeting")
                .putExtra(
                    Intent.EXTRA_TEXT,
                    "Hello everyone here, I'm trying to send you an email from my Android app"
                )
            startActivity(Intent.createChooser(intent, "Send email using..."))
        }

        findViewById<Button>(R.id.receiver_btn).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
                .addCategory(Intent.CATEGORY_DEFAULT)
                .setType("text/plain")
            generatePayload(intent)
            startActivity(intent)
        }
    }


    private fun generatePayload(intent: Intent) {
        intent
            .putExtra("title", "Dune: Part One")
            .putExtra("year","2021")
            .putExtra("description","Фильм, основанный на одноимённом фантастическом романе Френка Герберта, расскажет историю Пола Атрейдеса — талантливого молодого человека, рождённого в состоятельной семье. Ему суждено великое будущее, которое он пока не в силах осознать. Однажды его семейство отправляют на далёкую и опасную планету Арракис, где среди бесконечных песчаных дюн добывают специю — психоделическое вещество, необходимое для межгалактических путешествий. Добыче мешают жуткие существа — огромные песчаные черви, реагирующие на звук и пожирающие всё на своём пути. Пока Атрейдесы осваиваются на планете, они не догадываются, что их отправили на верную смерть...")
    }
}