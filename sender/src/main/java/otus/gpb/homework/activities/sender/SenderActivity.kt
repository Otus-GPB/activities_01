package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val btnToMap = findViewById<Button>(R.id.button_to_Google_Map)
        val uriRestaurant = Uri.parse("geo:0, 0?q=Рестораны")
        btnToMap.setOnClickListener {
            val mapIntent = Intent(Intent.ACTION_VIEW, uriRestaurant)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        val btnSendMail = findViewById<Button>(R.id.button_send_Email)
        val mail = "android@otus.ru"
        btnSendMail.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(mail)) //Почему, если написать putExtra(Intent.EXTRA_EMAIL, "android@otus.ru")
                                            // или putExtra(Intent.EXTRA_EMAIL, mail), то почта не добавляется в получателя?
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Simple Subject")
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Simple text from sender app!")
            sendIntent.type = "message/rfc822"

            startActivity(Intent.createChooser(sendIntent, "Send mail using..."));
        }
    }
}