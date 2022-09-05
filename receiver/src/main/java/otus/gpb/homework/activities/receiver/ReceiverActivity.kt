package otus.gpb.homework.activities.receiver

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        val posterImageView = findViewById<ImageView>(R.id.posterImageView)
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)
        val yearTextView = findViewById<TextView>(R.id.yearTextView)
        if (intent?.action == Intent.ACTION_SEND && intent?.type == "text/plain"){
            val title = intent.getStringExtra("title")
            val year = intent.getStringExtra("year")
            val description = intent.getStringExtra("description")
            val image = if (title == "Интерстеллар"){
                getDrawable(R.drawable.interstellar)
            } else{
                getDrawable(R.drawable.niceguys)
            }
            posterImageView.setImageDrawable(image)
            titleTextView.text = title
            descriptionTextView.text = description
            yearTextView.text = year.toString()
        }
    }
}