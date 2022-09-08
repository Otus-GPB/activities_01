package otus.gpb.homework.activities


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import kotlinx.parcelize.Parcelize

class FillFormActivity : AppCompatActivity() {
    lateinit var user_object: UserFormData
    lateinit var user_form_result: Intent
    lateinit var text_firstname: EditText
    lateinit var text_secondname: EditText
    lateinit var text_age: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user_object = savedInstanceState?.getParcelable<UserFormData>(KEY_OUTPUT_VALUE)
            ?: intent.getParcelableExtra<UserFormData>(INPUT_VALUE)
                    ?: UserFormData("", "", 0)
        setContentView(R.layout.activity_fill_form)
        text_firstname = findViewById(R.id.edit_firstname)
        text_secondname = findViewById(R.id.edit_secondname)
        text_age = findViewById(R.id.edit_age)

        if (user_object.realCreate == true) {
            updateUI()
        }
        text_age.setOnEditorActionListener { v, actionId, _ ->
            saveUser()
            true
        }

        val b_save = findViewById<Button>(R.id.save_user)
        b_save.setOnClickListener {
            saveUser()
        }
    }

    private fun updateUI() {
        text_firstname.setHint("...редактировать: ${user_object.firstname.toString()}")
        text_secondname.setHint("...редактировать: ${user_object.secondname.toString()}")
        text_age.setHint("...редактировать: ${user_object.age.toString()}")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OUTPUT_VALUE, user_object)
    }

    private fun saveUser() {
        if (text_firstname.text.trim().isEmpty())
            text_firstname.error = "ваше имя?"
        else if (text_secondname.text.trim().isEmpty())
            text_secondname.error = "ваша фамилия?"
        else if (text_age.text.trim().isEmpty())
            text_age.error = "ваш возраст?"
        else {
            user_object = UserFormData(
                text_firstname.text.toString().trim(),
                text_secondname.text.toString().trim(),
                text_age.text.toString().trim().toInt()
            )
            user_form_result = Intent()
            user_form_result.putExtra(OUTPUT_VALUE, user_object)
            setResult(RESULT_OK, user_form_result)
            finish()
        }
    }

    @Parcelize
    data class UserFormData(
        val firstname: String,
        val secondname: String,
        val age: Int,
        val realCreate: Boolean = true
    ) :
        Parcelable {
        override fun toString(): String {
            return ("Имя: $firstname\nФамилия: $secondname\nВозраст:$age")
        }

        companion object {
            val USER_DEFAULT = UserFormData("Default", "Default", 0, false)

        }
    }


    class FillFormContract : ActivityResultContract<UserFormData, UserFormData?>() {


        override fun createIntent(context: Context, input: UserFormData): Intent {
            val intent = Intent(context, FillFormActivity::class.java)
            intent.putExtra(INPUT_VALUE, input)
            return intent
        }

        override fun parseResult(resultCode: Int, intent: Intent?): UserFormData? {
            if (intent == null) return null
            val message = intent?.getParcelableExtra<UserFormData>(OUTPUT_VALUE) ?: null
            return message

        }

    }

    companion object {
        private const val INPUT_VALUE = "input_value"
        private const val OUTPUT_VALUE = "output_value"
        private const val KEY_OUTPUT_VALUE = "key_output_value"
    }

}