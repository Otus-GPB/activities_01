package otus.gpb.homework.activities.sender

import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream

private const val EXTRA_TITLE = "title"
private const val EXTRA_YEAR = "year"
private const val EXTRA_DESCRIPTION = "description"

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
        btnSendMail.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(resources.getString(R.string.email))) //Почему, если написать putExtra(Intent.EXTRA_EMAIL, "android@otus.ru")
                                            // или putExtra(Intent.EXTRA_EMAIL, resources.getString(R.string.email)), то почта не добавляется в получателя?
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.subject))
            sendIntent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.text))
            sendIntent.type = "message/rfc822"

            startActivity(sendIntent)
        }

        val btnToReceiver = findViewById<Button>(R.id.button_open_Reveiver)
        btnToReceiver.setOnClickListener {
            val openIntent = Intent(Intent.ACTION_SEND)
            openIntent.type = "text/plain"
            openIntent.addCategory(Intent.CATEGORY_DEFAULT)
            openIntent.putExtra(EXTRA_TITLE, resources.getString(R.string.film_title))
            openIntent.putExtra(EXTRA_YEAR, resources.getString(R.string.film_year))
            openIntent.putExtra(EXTRA_DESCRIPTION, resources.getString(R.string.film_description))

            startActivity(openIntent)
        }

    }
}