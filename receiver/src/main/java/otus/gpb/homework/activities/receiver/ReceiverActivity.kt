package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        val title = intent.getStringExtra(TITLE)
        findViewById<TextView>(R.id.titleTextView).text = title  ?: "Title (no data received)"
        findViewById<TextView>(R.id.descriptionTextView).text = intent.getStringExtra(DESCRIPTION) ?: "Description (no data received)"
        findViewById<TextView>(R.id.yearTextView).text = intent.getStringExtra(YEAR) ?: "Year (no data received)"

        val poster = when (title) {
            resources.getString(R.string.interstellar) -> R.drawable.interstellar
            resources.getString(R.string.niceguys) -> R.drawable.niceguys
            else -> R.drawable.ic_launcher_foreground
        }
        findViewById<ImageView>(R.id.posterImageView).setImageResource(poster)
    }


    companion object {
        private const val TITLE = "title"
        private const val YEAR = "year"
        private const val DESCRIPTION = "description"
    }
}