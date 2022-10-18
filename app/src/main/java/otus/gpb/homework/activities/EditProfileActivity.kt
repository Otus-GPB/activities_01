package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var uri: Uri
    private lateinit var imageView: ImageView
    private val cameraRequestLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it)
                initializePicture()
            else if (!shouldShowRationale()) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Доигрался")
                    .setMessage("Теперь иди в конфиги")
                    .setPositiveButton("Ладно, ладно...") { _, _ ->
                        openApplicationSettings()
                    }
                    .show()
            }
        }

    private val photoFromGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {
                uri = it
                populateImage(it)
            }
        }

    private val editProfileButton =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == 200) {
                findViewById<TextView>(R.id.textview_name).text = it.data?.getStringExtra("name")
                findViewById<TextView>(R.id.textview_surname).text = it.data?.getStringExtra("lastName")
                findViewById<TextView>(R.id.textview_age).text = it.data?.getStringExtra("age")

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Choose your destiny")
                .setItems(arrayOf("Сделать фото", "Выбрать фото")) {_, which ->
                    if (which == 0) {
                        requestPermissionWithRationale()
                    } else {
                        photoFromGalleryLauncher.launch("image/*")
                    }
                }
                .show()
        }

        findViewById<Button>(R.id.button4).setOnClickListener {
            editProfileButton.launch(Intent(this, FillFromActivity::class.java))
        }

        findViewById<Toolbar>(R.id.toolbar).apply {
            inflateMenu(R.menu.menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.send_item -> {
                        openSenderApp()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        val name = findViewById<TextView>(R.id.textview_name).text.toString()
        val lastName = findViewById<TextView>(R.id.textview_surname).text.toString()
        val age = findViewById<TextView>(R.id.textview_age).text.toString()

        val intent = Intent(Intent.ACTION_SEND)
            .putExtra(Intent.EXTRA_TEXT, "$name\n$lastName\n$age")
            .putExtra(Intent.EXTRA_STREAM, uri)
            .setPackage("org.telegram.messenger")
            .setType("image/*")
        startActivity(Intent.createChooser(intent, "Select TELEGRAM"))
    }

    private fun requestPermissionWithRationale() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            initializePicture()
        } else if (shouldShowRationale()) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Разрешение на камеру можно?")
                .setMessage("Ооочень надо")
                .setNegativeButton("Ни за что!") { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton("Так и быть") { _, _ ->
                    cameraRequestLauncher.launch(Manifest.permission.CAMERA)
                }
                .show()
        } else
            cameraRequestLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun openApplicationSettings() {
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:$packageName")
        )
        startActivity(appSettingsIntent)
    }

    private fun initializePicture() {
        imageView.setImageDrawable(getDrawable(R.drawable.cat))
    }

    private fun shouldShowRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(
            this, Manifest.permission.CAMERA
        )
}