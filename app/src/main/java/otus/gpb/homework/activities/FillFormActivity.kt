package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.databinding.ActivityFillFormBinding

class FillFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFillFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFillFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonApply.setOnClickListener {
            val intent = Intent()
                .putExtra(EXTRA_NAME_KEY, binding.edittextName.text.toString())
                .putExtra(EXTRA_SURNAME_KEY, binding.edittextSurname.text.toString())
                .putExtra(EXTRA_AGE_KEY, binding.edittextAge.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}