package otus.gpb.homework.activities

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                makePhoto()
            }
            else if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.CAMERA)
            ) {
                showSettingsDialog()
            }
        }

    private val settingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()
        ) {
            cameraLauncher.launch(Manifest.permission.CAMERA)
        }

    private val fillFormLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult? ->
            if (result?.resultCode == Activity.RESULT_OK) {
                val intent: Intent? = result.data
                findViewById<TextView>(R.id.textview_name).text =
                    intent?.getStringExtra(FillFormActivity.EXTRA_RESULT_FIRST_NAME)
                findViewById<TextView>(R.id.textview_surname).text =
                    intent?.getStringExtra(FillFormActivity.EXTRA_RESULT_SECOND_NAME)
                findViewById<TextView>(R.id.textview_age).text =
                    intent?.getStringExtra(FillFormActivity.EXTRA_RESULT_AGE)
            }
        }

    private val selectImageFromGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                populateImage(uri)
                imageView.tag = uri.toString()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        imageView.tag = "NO URI"
        customizeImageView()
        findViewById<Button>(R.id.button4).setOnClickListener {
            fillFormLauncher.launch(Intent(this, FillFormActivity::class.java))
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

    private fun customizeImageView() {
        imageView.setOnClickListener {
            val items = arrayOf("Сделать Фото", "Выбрать Фото")
            MaterialAlertDialogBuilder(this)
                .setTitle("Пожалуйста, выберите")
                .setItems(items) { _, idx ->
                    if (idx == 0) {
                        requestCameraPermissionWithRationale()
                    }
                    else {
                        selectImageFromGalleryLauncher.launch("image/*")
                    }
                }
                .show()
        }
    }

    private fun makePhoto() {
        imageView.setImageResource(R.drawable.cat)
    }

    private fun openApplicationSettings() {
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:$packageName")
        )
        settingsLauncher.launch(appSettingsIntent)
    }

    private fun requestCameraPermissionWithRationale() {
        when {
            ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED -> {
                makePhoto()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.CAMERA
            ) -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Пожалуйста, предоставьте доступ.")
                    .setMessage("Это необходимо, чтобы сделать фото.")
                    .setNegativeButton("Отмена") { dialog, _ ->
                        dialog.cancel()
                    }
                    .setPositiveButton("Дать доступ") { _, _ ->
                        cameraLauncher.launch(Manifest.permission.CAMERA)
                    }
                    .show()
            }
            else -> {
                cameraLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        var text: String = findViewById<TextView>(R.id.textview_name).text.toString() + "\n"
        text += findViewById<TextView>(R.id.textview_surname).text.toString() + "\n"
        text += findViewById<TextView>(R.id.textview_age).text.toString() + "\n"
        val uri: Uri? =
            if (imageView.tag.toString() == "NO URI")
                null
            else
                Uri.parse(imageView.tag.toString())
        intent = Intent(Intent.ACTION_SEND).apply {
            type = "*/*"
            `package` = "org.telegram.messenger"
            if (uri != null)
                putExtra(Intent.EXTRA_STREAM, Uri.parse(imageView.tag.toString()))
            putExtra(Intent.EXTRA_TEXT, text)
        }
        try {
            startActivity(intent)
        }
        catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Telegram not found: $e", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSettingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("В настройках перейдите на вкладку разрешения")
            .setMessage("И разрешите использовать камеру")
            .setPositiveButton("Открыть настройки") {_, _ ->
                openApplicationSettings()
            }
            .show()
    }
}