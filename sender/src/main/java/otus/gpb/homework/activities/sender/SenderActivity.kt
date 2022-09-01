package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

private const val TITLE = "title"
private const val YEAR = "year"
private const val DESCRIPTION = "description"

private const val MAPS_PACKAGE = "com.google.android.apps.maps"
private const val MAPS_QUERY = "geo:0,0?q=restaurants"

private const val MESSAGE_TYPE = "message/rfc822"
private const val PLAIN_TEXT_TYPE = "text/plain"

class SenderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.open_maps).setOnClickListener {
            val restaurantsUri = Uri.parse(MAPS_QUERY)
            val intent = Intent(Intent.ACTION_VIEW, restaurantsUri)
            intent.setPackage(MAPS_PACKAGE)

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                showErrorToast(getString(R.string.error_no_map_app))
            }
        }

        findViewById<Button>(R.id.send_email).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.apply {
                type = MESSAGE_TYPE
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_address)))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.email_text))
            }

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                showErrorToast(getString(R.string.error_no_email_app))
            }
        }

        findViewById<Button>(R.id.open_receiver).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.apply {
                type = PLAIN_TEXT_TYPE
                addCategory(Intent.CATEGORY_DEFAULT)
                putExtra(TITLE, getString(R.string.movie_title))
                putExtra(YEAR, getString(R.string.movie_year))
                putExtra(DESCRIPTION, getString(R.string.movie_description))
            }

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                showErrorToast(getString(R.string.error_no_receiver_app))
            }
        }

    }

    private fun showErrorToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
