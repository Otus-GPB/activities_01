package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

const val NAME = "NAME"
const val S_NAME = "S_NAME"
const val AGE = "AGE"

class FillFormActivity : AppCompatActivity() {


    private lateinit var editName: EditText
    private lateinit var editSname: EditText
    private lateinit var editAge: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        editName = findViewById(R.id.edit_name)
        editSname = findViewById(R.id.edit_sname)
        editAge = findViewById(R.id.edit_age)

        findViewById<Button>(R.id.button_apply).setOnClickListener { returnResult() }

    }

    private fun returnResult() {
        val result = Intent()
            .putExtra(NAME, editName.text.toString())
            .putExtra(S_NAME, editSname.text.toString())
            .putExtra(AGE, editAge.text.toString())
        setResult(RESULT_OK, result)
        finish()
    }
}