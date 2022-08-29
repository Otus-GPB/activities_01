package otus.gpb.homework.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ActivityD : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d)
        supportActionBar?.title = "D"
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w(localClassName, "ON_DESTROY D")
    }
}