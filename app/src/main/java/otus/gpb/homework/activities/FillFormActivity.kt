package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FillFormActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_RESULT_FIRST_NAME = "extra_result_first_name"
        const val EXTRA_RESULT_SECOND_NAME = "extra_result_second_name"
        const val EXTRA_RESULT_AGE = "extra_result_age"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        findViewById<Button>(R.id.buttonApply).setOnClickListener() {
            returnResult()
            finish()
        }
    }

    private fun returnResult() {
        val firstName: String = findViewById<EditText>(R.id.editTextFirstName).text.toString()
        val secondName: String = findViewById<EditText>(R.id.editTextSecondName).text.toString()
        val age: String = findViewById<EditText>(R.id.editTextAge).text.toString()
        val result = Intent()
            .putExtra(EXTRA_RESULT_FIRST_NAME, firstName)
            .putExtra(EXTRA_RESULT_SECOND_NAME, secondName)
            .putExtra(EXTRA_RESULT_AGE, age)
        setResult(RESULT_OK, result)
    }


}