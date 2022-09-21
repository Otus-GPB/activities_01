package ru.desh.activities2.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import ru.desh.domain.Payload

class ReceiverActivity : AppCompatActivity() {
    companion object{
        const val TAG = "ReceiverActivity"

        private const val EXTRA_TITLE = "title"
        private const val EXTRA_YEAR = "year"
        private const val EXTRA_DESCRIPTION = "description"
        private const val EXTRA_PAYLOAD = "payload"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        handleIntent()
    }

    private fun handleIntent() {
        intent.extras?.getParcelable<Payload>(EXTRA_PAYLOAD)?.also {
                findViewById<TextView>(R.id.titleTextView).text = it.title
                findViewById<TextView>(R.id.descriptionTextView).text = it.description
                findViewById<TextView>(R.id.yearTextView).text = it.year
                val pic = AppCompatResources.getDrawable(this,
                    if (it.title =="Интерстеллар")
                        R.drawable.interstellar
                    else
                        R.drawable.niceguys
                )
                findViewById<ImageView>(R.id.posterImageView).setImageDrawable(pic)
        }
    }
}
