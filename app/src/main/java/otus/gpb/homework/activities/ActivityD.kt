package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ActivityD : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d)
        Log.i("CREATED: ", this.toString())
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i("ON INTENT: ", this.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("DESTROYED: ", this.toString())
    }
}