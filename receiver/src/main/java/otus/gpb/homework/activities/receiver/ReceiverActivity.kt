package otus.gpb.homework.activities.receiver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.receiver.databinding.ActivityReceiverBinding

class ReceiverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReceiverBinding

    companion object {
        const val MOVIE_TITLE = "TITLE"
        const val MOVIE_YEAR = "YEAR"
        const val MOVIE_DESCRIPTION = "DESCRIPTION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        drawContent()
    }

    private fun drawContent() {
        val title = intent.getStringExtra(MOVIE_TITLE) ?: return
        val image = when (title) {
            "Славные парни" -> R.drawable.niceguys
            "Интерстеллар" -> R.drawable.interstellar
            else -> throw IllegalArgumentException()
        }

        binding.apply {
            titleTextView.text = title
            yearTextView.text = intent.getStringExtra(MOVIE_YEAR)
            descriptionTextView.text = intent.getStringExtra(MOVIE_DESCRIPTION)
            posterImageView.setImageResource(image)
        }
    }
}