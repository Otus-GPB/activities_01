package otus.gpb.homework.activities.receiver

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        findViewById<TextView>(R.id.titleTextView).setText(intent.getStringExtra("title"))
        findViewById<TextView>(R.id.yearTextView).setText(intent.getStringExtra("year"))
        findViewById<TextView>(R.id.descriptionTextView).setText(intent.getStringExtra("description"))

        val poster = ContextCompat.getDrawable(this, R.drawable.niceguys)
        findViewById<ImageView>(R.id.posterImageView).setImageDrawable(poster)
    }
}