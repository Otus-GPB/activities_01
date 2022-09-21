package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val txtTitle = findViewById<TextView>(R.id.titleTextView)
        val title = intent.extras?.getString("title")
        txtTitle.text = title

        val txtYear = findViewById<TextView>(R.id.yearTextView)
        txtYear.text = intent.extras?.getString("year")

        val txtDescription = findViewById<TextView>(R.id.descriptionTextView)
        txtDescription.text = intent.extras?.getString("description")

        val image = when (title) {
            "Славные парни" -> R.drawable.niceguys
            "Интерстеллар" -> R.drawable.interstellar
            else -> R.drawable.ic_launcher_background
        }

        val imageView = findViewById<ImageView>(R.id.posterImageView)
        imageView.setImageResource(image)
    }
}