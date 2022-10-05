package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class FillFormActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_FIRST_NAME = "EXTRA_FIRST_NAME"
        const val EXTRA_SECOND_NAME = "EXTRA_SECOND_NAME"
        const val EXTRA_AGE = "EXTRA_AGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        findViewById<Button>(R.id.buttonApply).setOnClickListener {
            setResult(RESULT_OK, Intent().apply {
                putExtra(EXTRA_FIRST_NAME, findViewById<EditText>(R.id.editTextFirstName).text.toString())
                putExtra(EXTRA_SECOND_NAME, findViewById<EditText>(R.id.editTextSecondName).text.toString())
                putExtra(EXTRA_AGE, findViewById<EditText>(R.id.editTextAge).text.toString())
            })
            finish()
        }
    }
}