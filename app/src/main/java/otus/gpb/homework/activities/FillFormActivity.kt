package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class FillFormActivity : AppCompatActivity() {

    private lateinit var formName: EditText
    private lateinit var formSurname: EditText
    private lateinit var formAge: EditText

    private val onClickApply = View.OnClickListener {
        val profile = Profile(
            name = formName.text.toString(),
            surname = formSurname.text.toString(),
            age = formAge.text.toString().toInt()
        )
        val resultData = Intent()
            .putExtra(EditProfileContract.EXTRA_MESSAGE, profile)
        setResult(RESULT_OK, resultData)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)
        formName = findViewById(R.id.fill_form_name)
        formSurname = findViewById(R.id.fill_form_surname)
        formAge = findViewById(R.id.fill_form_age)
        findViewById<Button>(R.id.fill_form_apply_button).setOnClickListener(onClickApply)
    }
}