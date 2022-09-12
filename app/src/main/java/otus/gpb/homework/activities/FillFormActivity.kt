package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FillFormActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var surname: EditText
    private lateinit var age: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)
        name = findViewById(R.id.editText_name)
        surname = findViewById(R.id.editText_surname)
        age = findViewById(R.id.editText_age)

        findViewById<Button>(R.id.button_apply).setOnClickListener { returnResult() }
    }

    private fun returnResult() {
        val result = Intent()
            .putExtra(NAME, name.text.toString())
            .putExtra(SURNAME, surname.text.toString())
            .putExtra(AGE, age.text.toString())

        setResult(RESULT_OK, result)
        finish()
    }

    companion object {
        const val NAME = "name_from_fill_form"
        const val SURNAME = "surname_from_fill_form"
        const val AGE = "age_from_fill_form"
    }
}