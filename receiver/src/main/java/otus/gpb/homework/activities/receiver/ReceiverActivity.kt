package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    companion object {
        const val ARG_TITLE = "ARG_TITLE"
        const val ARG_YEAR = "ARG_YEAR"
        const val ARG_DESCRIPTION = "ARG_DESCRIPTION"
    }

    private val title: String by lazy { intent.getStringExtra(ARG_TITLE) ?: "Unknown title" }
    private val year: String by lazy { intent.getStringExtra(ARG_YEAR) ?: "-" }
    private val description: String by lazy { intent.getStringExtra(ARG_DESCRIPTION) ?: "Unknown description" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        updateUi()
    }

    private fun updateUi() {
        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.yearTextView).text = year
        findViewById<TextView>(R.id.descriptionTextView).text = description
        findViewById<ImageView>(R.id.posterImageView).setImageResource(getDrawableIdByMovieName(title))
    }

    @DrawableRes
    private fun getDrawableIdByMovieName(name: String) : Int =
        when (name) {
            "Интерстеллар" -> R.drawable.interstellar
            "Славные парни" -> R.drawable.niceguys
            else -> 0
        }
}
