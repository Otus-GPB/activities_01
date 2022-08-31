package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        findViewById<Button>(R.id.buttonC_A).setOnClickListener{
            startActivity(Intent(this, ActivityA::class.java))
        }

        findViewById<Button>(R.id.buttonC_D).setOnClickListener{
            startActivity(Intent(this, ActivityD::class.java))
        }

        findViewById<Button>(R.id.buttonC_C).setOnClickListener{
            this.finish()
        }

        findViewById<Button>(R.id.buttonC_Stack).setOnClickListener{
            finishAffinity()
        }
    }
}