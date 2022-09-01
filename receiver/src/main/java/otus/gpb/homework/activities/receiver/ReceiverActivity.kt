package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private const val EXTRA_TITLE = "title"
private const val EXTRA_YEAR = "year"
private const val EXTRA_DESCRIPTION = "description"

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        setViewAttributes()
    }

    private fun setViewAttributes() {
        val title = intent.getStringExtra(EXTRA_TITLE) ?: ""
        val year = intent.getStringExtra(EXTRA_YEAR) ?: ""
        val description = intent.getStringExtra(EXTRA_DESCRIPTION) ?: ""
        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.yearTextView).text = year
        findViewById<TextView>(R.id.descriptionTextView).text = description
        if (title.isNotEmpty()) {
            val drawableResource = when (title) {
                    resources.getString(R.string.title01) -> R.drawable.niceguys
                    resources.getString(R.string.title02) -> R.drawable.interstellar
                    else -> throw IllegalArgumentException("Unknown title: $title")
                }
            findViewById<ImageView>(R.id.posterImageView).setImageResource(drawableResource)
        }
    }
}