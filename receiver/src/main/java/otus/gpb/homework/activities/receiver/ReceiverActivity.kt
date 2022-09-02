package otus.gpb.homework.activities.receiver

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView

class ReceiverActivity : AppCompatActivity() {
    companion object{
        val KEY_PAYT="Title"
        val KEY_PAYY="Year"
        val KEY_PAYA="About"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val intent:HashMap<String,String> = getIntent().getSerializableExtra(Intent.EXTRA_TEXT) as HashMap<String, String>
        findViewById<TextView>(R.id.yearTextView).text=intent.get(KEY_PAYY)
        findViewById<TextView>(R.id.descriptionTextView).text=intent.get(KEY_PAYA)
        findViewById<TextView>(R.id.titleTextView).text=intent.get(KEY_PAYT)
        val text =findViewById<TextView>(R.id.titleTextView).text

        when(text){
            "Славные парни" ->  findViewById<ImageView>(R.id.posterImageView).setImageResource(R.drawable.niceguys)
            "Интерстеллар" -> findViewById<ImageView>(R.id.posterImageView).setImageResource(R.drawable.interstellar)
        }
    }
}