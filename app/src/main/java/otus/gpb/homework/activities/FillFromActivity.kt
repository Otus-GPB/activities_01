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

        findViewById<Button>(R.id.button_apply).setOnClickListener(){
            val intent = Intent()
                .putExtra("name", findViewById<EditText>(R.id.editTextName).text.toString())
                .putExtra("surname", findViewById<EditText>(R.id.editTextSurname).text.toString())
                .putExtra("age", findViewById<EditText>(R.id.editTextAge).text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}