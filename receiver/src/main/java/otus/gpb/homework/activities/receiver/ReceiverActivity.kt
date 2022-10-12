package otus.gpb.homework.activities.receiver

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)


        var title = this.intent.extras?.get("title").toString()
        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.yearTextView).text = this.intent.extras?.get("year").toString()
        findViewById<TextView>(R.id.descriptionTextView).text = this.intent.extras?.get("description").toString()
        if (title == "Интерстеллар")
        findViewById<ImageView>(R.id.posterImageView).setImageDrawable(getDrawable(R.drawable.interstellar))
else
        findViewById<ImageView>(R.id.posterImageView).setImageDrawable(getDrawable(R.drawable.niceguys))

      // Bundle arguments = getIntent().getExtras();

    }
}