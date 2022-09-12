package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FillFromActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_from)
        findViewById<Button>(R.id.acceptButton).setOnClickListener {
            returnResult()
            finish()
        }
    }

    private fun returnResult() {
        val name = findViewById<EditText>(R.id.nameEditText).text.toString()
        val lastName = findViewById<EditText>(R.id.lastNameEditText).text.toString()
        val age = findViewById<EditText>(R.id.ageEditText).text.toString()

        val result = Intent()
            .putExtra("name", name)
            .putExtra("lastName", lastName)
            .putExtra("age", age)
        setResult(200, result)
    }
}