package otus.gpb.homework.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContract
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    val name: String,
    val surname: String,
    val age: Int
) : Parcelable

class EditProfileContract: ActivityResultContract<String?, Profile?>() {
    companion object{
        const val EXTRA_MESSAGE = "extra_message"
    }
    override fun createIntent(context: Context, input: String?): Intent {
        return Intent(context, FillFormActivity::class.java)
            .putExtra(EXTRA_MESSAGE, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Profile? {
        if (intent == null) return null
        if (resultCode != Activity.RESULT_OK) return null

        return intent.getParcelableExtra(EXTRA_MESSAGE)
    }
}