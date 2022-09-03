package otus.gpb.homework.activities.receiver

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ReceiverActivity : AppCompatActivity() {
    companion object {
        const val APP_EXTRA_TITLE = "title"
        const val APP_EXTRA_YEAR = "year"
        const val APP_EXTRA_DESCRIPTION = "description"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        intent.getStringExtra(APP_EXTRA_TITLE)?.let {
            findViewById<TextView>(R.id.titleTextView).text = it

            var image: Drawable? = if (it == resources.getString(R.string.first_movie)) {
                ContextCompat.getDrawable(this, R.drawable.niceguys)
            } else if (it == resources.getString(R.string.second_movie)) {
                ContextCompat.getDrawable(this, R.drawable.interstellar)
            } else {
                null
            }

            if (image != null) {
                findViewById<ImageView>(R.id.posterImageView).setImageDrawable(image)
            }
        }

        intent.getStringExtra(APP_EXTRA_YEAR)?.let {
            findViewById<TextView>(R.id.yearTextView).text = it
        }

        intent.getStringExtra(APP_EXTRA_DESCRIPTION)?.let {
            findViewById<TextView>(R.id.descriptionTextView).text = it
        }
    }
}