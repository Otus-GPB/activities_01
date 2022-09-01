package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

private const val TITLE = "title"
private const val YEAR = "year"
private const val DESCRIPTION = "description"

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val title = intent.getStringExtra(TITLE)
        val description = intent.getStringExtra(DESCRIPTION)
        val year = intent.getStringExtra(YEAR)

        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.descriptionTextView).text = description
        findViewById<TextView>(R.id.yearTextView).text = year

        findViewById<ImageView>(R.id.posterImageView).setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.interstellar)
        )
    }
}