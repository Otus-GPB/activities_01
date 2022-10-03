package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import kotlinx.parcelize.Parcelize

class FillFormActivity : AppCompatActivity() {
    lateinit var userObject: UserFormData
    lateinit var userFormresult: Intent
    lateinit var textFirstname: EditText
    lateinit var textSecondname: EditText
    lateinit var textAge: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userObject = savedInstanceState?.getParcelable<UserFormData>(KEY_OUTPUT_VALUE)
            ?: intent.getParcelableExtra<UserFormData>(INPUT_VALUE) ?: UserFormData("", "", 0)
        setContentView(R.layout.activity_fill_form)
        textFirstname = findViewById(R.id.edit_firstname)
        textSecondname = findViewById(R.id.edit_secondname)
        textAge = findViewById(R.id.edit_age)

        if (userObject.realCreate == true) {
            updateUI()
        }
        textAge.setOnEditorActionListener { v, actionId, _ ->
            saveUser()
            true
        }

        val bSave = findViewById<Button>(R.id.save_user)
        bSave.setOnClickListener {
            saveUser()
        }
    }

    private fun updateUI() {
        textFirstname.setHint("${getString(R.string.helpText)} ${userObject.firstname}")
        textSecondname.setHint("${getString(R.string.helpText)} ${userObject.secondname}")
        textAge.setHint("${getString(R.string.helpText)} ${userObject.age}")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OUTPUT_VALUE, userObject)
    }

    private fun saveUser() {
        if (textFirstname.text.trim().isEmpty())
            textFirstname.error = getString(R.string.askUserFirstName)
        else if (textSecondname.text.trim().isEmpty())
            textSecondname.error = getString(R.string.askUserSecondName)
        else if (textAge.text.trim().isEmpty())
            textAge.error = getString(R.string.askUserAge)
        else {
            addUserObjForSave()
        }
    }

    private fun addUserObjForSave() {
        userObject = UserFormData(
            textFirstname.text.toString().trim(),
            textSecondname.text.toString().trim(),
            textAge.text.toString().trim().toInt()
        )
        userFormresult = Intent()
        userFormresult.putExtra(OUTPUT_VALUE, userObject)
        setResult(RESULT_OK, userFormresult)
        finish()
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
