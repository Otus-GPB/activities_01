package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.buttonToGoogleMaps).setOnClickListener { chooseRestaurant() }
        findViewById<Button>(R.id.buttonSendEmail).setOnClickListener { sendEmail()}
        findViewById<Button>(R.id.buttonOpenReceiver).setOnClickListener { openReceiver() }
    }

    private fun chooseRestaurant() {
        val gmmIntentUri = Uri.parse("geo:0,0?q=restaurants")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            .setPackage("com.google.android.apps.maps")
        try {
            startActivity(mapIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Google Maps not found.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendEmail() {
        val emailRecipient = resources.getString(R.string.email_recipient)
        val emailSubject = resources.getString(R.string.email_subject)
        val emailText = resources.getString(R.string.email_text)
        val chooserTitle = resources.getString(R.string.chooser_title)
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SENDTO
            data = Uri.parse("mailto:$emailRecipient")
            putExtra(Intent.EXTRA_EMAIL, emailRecipient)
            putExtra(Intent.EXTRA_SUBJECT, emailSubject)
            putExtra(Intent.EXTRA_TEXT, emailText)
        }
        try {
            startActivity(Intent.createChooser(sendIntent, chooserTitle))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "You can't send an email.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openReceiver() {
        val payload = getPayload()
        val chooserTitleOpen = resources.getString(R.string.chooser_title_open)
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            addCategory(Intent.CATEGORY_DEFAULT)
            putExtra("title", payload.title)
            putExtra("year", payload.year)
            putExtra("description", payload.description)
        }
        try {
            startActivity(Intent.createChooser(sendIntent, chooserTitleOpen))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "You can't open receiver.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getPayload(): Payload {
        val accident = (1..100).random() % 2
        val title =
            if (accident == 0)
                resources.getString(R.string.title01)
            else
                resources.getString(R.string.title02)
        val year =
            if (accident == 0)
                resources.getString(R.string.year01)
            else
                resources.getString(R.string.year02)
        val description =
            if (accident == 0)
                resources.getString(R.string.description01)
            else
                resources.getString(R.string.description02)

        return Payload(title, year, description)
    }
}