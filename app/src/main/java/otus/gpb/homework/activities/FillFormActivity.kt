package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FillFormActivity : AppCompatActivity(R.layout.activity_fill_form) {

    companion object {
        const val FILL_FORM_USER_NAME_ARG = "ARG_NAME"
        const val FILL_FORM_USER_SURNAME_ARG = "ARG_SURNAME"
        const val FILL_FORM_USER_AGE_ARG = "ARG_AGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<Button>(R.id.apply_form_button).setOnClickListener {
            returnResult()
            finish()
        }
    }

    private fun returnResult() {
        val name = findViewById<EditText>(R.id.edit_text_person_name).text.toString()
        val surname = findViewById<EditText>(R.id.edit_text_person_surname).text.toString()
        val age = findViewById<EditText>(R.id.edit_text_person_age).text.toString()

        val intent = Intent()
            .putExtra(FILL_FORM_USER_NAME_ARG, name)
            .putExtra(FILL_FORM_USER_SURNAME_ARG, surname)
            .putExtra(FILL_FORM_USER_AGE_ARG, age)

        setResult(RESULT_OK, intent)
    }
}
