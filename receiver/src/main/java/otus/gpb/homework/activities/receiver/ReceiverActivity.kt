package otus.gpb.homework.activities.receiver

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val title = intent.getStringExtra("title")
        val year = intent.getStringExtra("year")
        val desc = intent.getStringExtra("description")

        setPayloadData(title, year, desc)

    }



    private fun setPayloadData(title: String?, year: String?, desc: String? = "Description not found") {
        findViewById<TextView>(R.id.titleTextView).text = title?: "Title not found"
        findViewById<TextView>(R.id.yearTextView).text = year?: "0000"
        findViewById<TextView>(R.id.descriptionTextView).text = desc?: "Description not found"
        when(title) {
            "Dune: Part One" -> setImage(R.drawable.dune)
            "Славные парни" -> setImage(R.drawable.niceguys)
            "Интерстеллар" -> setImage(R.drawable.interstellar)
            else -> setImage(R.drawable.notfound)
        }
    }

    private fun setImage(id: Int) {
        val drawable: Drawable? = ContextCompat.getDrawable(this,id)
        findViewById<ImageView>(R.id.posterImageView).setImageDrawable(drawable)
    }
}