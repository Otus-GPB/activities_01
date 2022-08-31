package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val title = intent.getStringExtra("EXTRA_TITLE")
        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.yearTextView).text = intent.getStringExtra("EXTRA_YEAR")
        findViewById<TextView>(R.id.descriptionTextView).text =
            intent.getStringExtra("EXTRA_DESCRIPTION")

        val currentDrawable = {
            if (title == "title: Интерстеллар") (getDrawable(R.drawable.interstellar))
            else getDrawable(R.drawable.niceguys)
        }

        findViewById<ImageView>(R.id.posterImageView).setImageDrawable(currentDrawable.invoke())
    }
}