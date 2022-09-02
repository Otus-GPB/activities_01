package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import otus.gpb.homework.activities.sender.databinding.ActivitySenderBinding

class SenderActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySenderBinding

    companion object {
        const val MOVIE_TITLE = "TITLE"
        const val MOVIE_YEAR = "YEAR"
        const val MOVIE_DESCRIPTION = "DESCRIPTION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonToGoogleMaps.setOnClickListener { toGoogleMaps(it) }
            buttonSendEmail.setOnClickListener { sendEmail(it) }
            buttonOpenReceiver.setOnClickListener { openReceiver(it) }
        }
    }

    private fun toGoogleMaps(view: View) {
        val intent = Intent(ACTION_VIEW, Uri.parse("geo:0,0?q=restaurants"))
            .setPackage("com.google.android.apps.maps")

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Snackbar.make(
                view,
                R.string.error_no_gmaps,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun sendEmail(view: View) {
        val intent = Intent(ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
            putExtra(Intent.EXTRA_SUBJECT, "Homework #2")
            putExtra(Intent.EXTRA_TEXT, "Completed")
        }

        try {
            startActivity(
                Intent.createChooser(
                    intent,
                    "Send email using..."
                )
            )
        } catch (ex: ActivityNotFoundException) {
            Snackbar.make(
                view,
                R.string.error_no_email_clients,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun openReceiver(view: View) {
        val intent = Intent(ACTION_SEND).apply {
            type = "text/plain"
            addCategory(Intent.CATEGORY_DEFAULT)
            putExtra(MOVIE_TITLE, "Интерстеллар")
            putExtra(MOVIE_YEAR, "2014")
            putExtra(
                MOVIE_DESCRIPTION,
                """Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному 
                            |кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая 
                            |предположительно соединяет области пространства-времени через большое расстояние) 
                            |в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека 
                            |и найти планету с подходящими для человечества условиями.""".trimMargin()
            )
        }

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Snackbar.make(
                view,
                R.string.error_no_receiver,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}