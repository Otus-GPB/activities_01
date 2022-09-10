package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
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
                    this, Manifest.permission.CAMERA)) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("В настройках перейдите на вкладку разрешения")
                    .setMessage("И разрешите использовать камеру")
                    .setPositiveButton("Открыть настройки") {_, _ ->
                        openApplicationSettings()
                    }
                    .show()
            }
        }

    private val settingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()
        ) {
            cameraLauncher.launch(Manifest.permission.CAMERA)
        }

    private val selectImageFromGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                populateImage(uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        customizeImageView()

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
    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")
    }
}