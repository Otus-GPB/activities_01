package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import android.widget.Button
import android.widget.EditText

import androidx.appcompat.app.AppCompatActivity


class FillFormActivity : AppCompatActivity() {
    private lateinit var person: Person
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)
        findViewById<Button>(R.id.save_button).setOnClickListener {
            onSavePressed()
        }
    }

    private fun onSavePressed() {

        person = Person(
            name = findViewById<EditText>(R.id.name_edit_text).text.toString(),
            surName = findViewById<EditText>(R.id.surname_edit_text).text.toString(),
            age = findViewById<EditText>(R.id.age_edit_text).text.toString()
        )
        val intent = Intent()
        intent.putExtra(EXTRA_OPTIONS, person)
        setResult(Activity.RESULT_OK, intent)
        finish()

    }


    companion object {
        const val EXTRA_OPTIONS = "EXTRA_OPTIONS"

    }
}