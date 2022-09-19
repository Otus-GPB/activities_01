package otus.gpb.homework.activities.receiver

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        when (intent.getStringExtra(EXTRA_TITLE)) {
            "Славные парни" -> findViewById<ImageView>(R.id.posterImageView).setImageDrawable(
                getDrawable(R.drawable.niceguys)
            )
            "Интерстеллар" -> findViewById<ImageView>(R.id.posterImageView).setImageDrawable(
                getDrawable(R.drawable.interstellar)
            )
        }
        findViewById<TextView>(R.id.titleTextView).text = intent.getStringExtra(EXTRA_TITLE)
        findViewById<TextView>(R.id.yearTextView).text = intent.getStringExtra(EXTRA_YEAR)
        findViewById<TextView>(R.id.descriptionTextView).text = intent.getStringExtra(
            EXTRA_DESCRIPTION
        )

    }

    companion object {
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_YEAR = "year"
        private const val EXTRA_DESCRIPTION = "description"
    }
}