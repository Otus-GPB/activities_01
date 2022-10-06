package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

const val FILL_FORM_USER_NAME_KEY = "Name"
const val FILL_FORM_USER_SURNAME_KEY = "Surname"
const val FILL_FORM_USER_AGE_KEY = "Age"

class FillFormActivity : AppCompatActivity() {
    private fun returnResult(name: String, surname: String, age: String) {
        val intent = Intent()
            .putExtra(FILL_FORM_USER_NAME_KEY, name)
            .putExtra(FILL_FORM_USER_SURNAME_KEY, surname)
            .putExtra(FILL_FORM_USER_AGE_KEY, age)
        setResult(RESULT_OK, intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        val name = findViewById<EditText>(R.id.editTextPersonName)
        val surname = findViewById<EditText>(R.id.editTextPersonSurname)
        val age = findViewById<EditText>(R.id.editTextPersonAge)

        findViewById<Button>(R.id.applyFormButton).setOnClickListener {
            returnResult(name.text.toString(), surname.text.toString(), age.text.toString())
            finish()
        }
    }
}
