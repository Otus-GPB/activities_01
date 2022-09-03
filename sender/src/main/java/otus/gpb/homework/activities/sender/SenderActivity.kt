package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.snackbar.Snackbar

class SenderActivity : AppCompatActivity() {
    companion object {
        const val MAP_APP_PACKAGE = "com.google.android.apps.maps"
        const val MAP_APP_QUERY = "geo:0,0?q="

        const val EMAIL_APP_SCHEME = "mailto:"

        const val APP_EXTRA_TITLE = "title"
        const val APP_EXTRA_YEAR = "year"
        const val APP_EXTRA_DESCRIPTION = "description"
    }


    private fun showNearbyPlaceOnMap(view: View, place: String) {
        val mapIntent = Intent(Intent.ACTION_VIEW)
            .setData(Uri.parse("$MAP_APP_QUERY $place"))
            .setPackage(MAP_APP_PACKAGE)

        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            Snackbar.make(view, R.string.error_map_app, Snackbar.LENGTH_LONG).show()
        }
    }


    private fun sendEmail(view: View, address: String, subject: String, message: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
            .setData(Uri.parse(EMAIL_APP_SCHEME))
            .putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
            .putExtra(Intent.EXTRA_SUBJECT, subject)
            .putExtra(Intent.EXTRA_TEXT, message)

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(emailIntent)
        } else {
            Snackbar.make(view, R.string.error_email_app, Snackbar.LENGTH_LONG).show()
        }
    }


    private fun sendToReceiver(view: View, payload: Payload) {
        val intent = Intent(Intent.ACTION_SEND)
            .addCategory(Intent.CATEGORY_DEFAULT)
            .setType("text/plain")
            .putExtra(APP_EXTRA_TITLE, payload.title)
            .putExtra(APP_EXTRA_YEAR, payload.year)
            .putExtra(APP_EXTRA_DESCRIPTION, payload.description)

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Snackbar.make(view, R.string.error_receiver_app, Snackbar.LENGTH_LONG).show()
        }
    }


    private fun getPayload(): Payload {
        return Payload(
            title = resources.getString(R.string.movie_title),
            year = resources.getString(R.string.movie_year),
            description = resources.getString(R.string.movie_description)
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.btn_gmaps).setOnClickListener {
            showNearbyPlaceOnMap(it, resources.getString(R.string.map_nearby))
        }
        findViewById<Button>(R.id.btn_send_email).setOnClickListener {
            val address = resources.getString(R.string.email_address)
            val subject = resources.getString(R.string.email_subject)
            val msg = resources.getString(R.string.email_message)
            sendEmail(it, address, subject, msg)
        }
        findViewById<Button>(R.id.btn_open_receiver).setOnClickListener {
            sendToReceiver(it, getPayload())
        }
    }
}

