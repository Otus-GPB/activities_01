package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)
        findViewById<Button>(R.id.buttona).setOnClickListener {
            startActivity(Intent(this, ActivityA :: class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

        }
        findViewById<Button>(R.id.buttond).setOnClickListener {
            startActivity(Intent(this, ActivityD :: class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags((Intent.FLAG_ACTIVITY_NEW_TASK)))
        }
        findViewById<Button>(R.id.button_closestack).setOnClickListener {
            finishAffinity()

            //startActivity(Intent(this, ActivityA :: class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))



        }
        findViewById<Button>(R.id.button_closec).setOnClickListener {


            finish();


        }



    }
}