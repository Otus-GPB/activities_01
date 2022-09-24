package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

const val USER_NAME = "name"
const val USER_SURNAME = "surname"
const val USER_AGE = "age"

class FillFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        findViewById<Button>(R.id.button_apply).setOnClickListener() {
            val resultIntent = Intent()
                .putExtra(USER_NAME, findViewById<EditText>(R.id.text_enter_name).text.toString())
                .putExtra(USER_SURNAME, findViewById<EditText>(R.id.text_enter_surname).text.toString())
                .putExtra(USER_AGE, findViewById<EditText>(R.id.text_enter_age).text.toString())
            setResult(RESULT_OK, resultIntent)

            finish()
        }
    }

}