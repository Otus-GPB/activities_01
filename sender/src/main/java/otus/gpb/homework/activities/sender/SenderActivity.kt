package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val btnToMap = findViewById<Button>(R.id.button_to_Google_Map)
        val uriRestaurant = Uri.parse("geo:0, 0?q=Рестораны")
        btnToMap.setOnClickListener {
            val mapIntent = Intent(Intent.ACTION_VIEW, uriRestaurant)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }
}