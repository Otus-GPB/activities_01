package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class FillFormActivity : AppCompatActivity() {

    private val onClickApply = View.OnClickListener {
        val profile = Profile(
            name = findViewById<EditText>(R.id.fill_form_name).text.toString(),
            surname = findViewById<EditText>(R.id.fill_form_surname).text.toString(),
            age = findViewById<EditText>(R.id.fill_form_age).text.toString().toInt()
        )
        val resultData = Intent()
            .putExtra(EditProfileContract.EXTRA_MESSAGE, profile)
        setResult(RESULT_OK, resultData)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)
        findViewById<Button>(R.id.fill_form_apply_button).setOnClickListener(onClickApply)
    }
}