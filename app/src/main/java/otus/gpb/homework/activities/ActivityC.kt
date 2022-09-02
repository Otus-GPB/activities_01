package otus.gpb.homework.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        findViewById<Button>(R.id.button_to_A).setOnClickListener {}

        findViewById<Button>(R.id.button_to_D).setOnClickListener {}

        findViewById<Button>(R.id.button_close_C).setOnClickListener {}

        findViewById<Button>(R.id.button_close_Stack).setOnClickListener {}
    }
}