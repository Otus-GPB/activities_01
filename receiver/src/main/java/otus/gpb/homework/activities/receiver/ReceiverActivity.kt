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

        if(intent?.type == "text/plain") {

            val title = intent.getStringExtra("title")
            val year = intent.getStringExtra("year")
            val description = intent.getStringExtra("description")
            val image = if(title == "Славные парни"){
                getDrawable(R.drawable.niceguys)
            }else{
                getDrawable(R.drawable.interstellar)
            }

            findViewById<TextView>(R.id.titleTextView).text = title
            findViewById<TextView>(R.id.yearTextView).text = year
            findViewById<TextView>(R.id.descriptionTextView).text = description
            findViewById<ImageView>(R.id.posterImageView).setImageDrawable(image)
        }

    }
}