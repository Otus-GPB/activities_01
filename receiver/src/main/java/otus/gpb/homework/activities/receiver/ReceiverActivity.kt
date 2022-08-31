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

        val title = intent.getStringExtra("title")

        findViewById<ImageView>(R.id.posterImageView)
            .setImageDrawable(
                getDrawable(if (title == "Интерстеллар") R.drawable.interstellar else R.drawable.niceguys)
            )

        findViewById<TextView>(R.id.titleTextView).text = title

        findViewById<TextView>(R.id.yearTextView).text = intent.getStringExtra("year")

        findViewById<TextView>(R.id.descriptionTextView).text = intent.getStringExtra("description")
    }
}