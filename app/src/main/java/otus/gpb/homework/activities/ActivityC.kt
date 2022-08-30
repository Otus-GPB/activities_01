package otus.gpb.homework.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        findViewById<Button>(R.id.open_a_from_c).setOnClickListener {

        }

        findViewById<Button>(R.id.open_d_from_c).setOnClickListener {

        }

        findViewById<Button>(R.id.close_c).setOnClickListener {

        }

        findViewById<Button>(R.id.close_stack).setOnClickListener {

        }
    }
}