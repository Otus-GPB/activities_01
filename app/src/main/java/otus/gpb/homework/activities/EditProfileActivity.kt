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
    private val getImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { populateImage(it) }
        }
    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)

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

        findViewById<ImageView>(R.id.imageview_photo).setOnClickListener {
            MaterialAlertDialogBuilder(this)
            val items = arrayOf("Сделать фото", "Выбрать из галереи")

            MaterialAlertDialogBuilder(this)
                .setTitle("Как будем загружать фото?")
                .setItems(items) { dialog, which ->
                    when (which) {
                        0 -> requestCameraPermission()
                        1 -> getImage.launch("image/*")
                    }
                }
                .show()
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

    private fun requestCameraPermission() {
        val isShould: Boolean =
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
        when {
            // разрешение есть
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                imageView.setImageResource(R.drawable.cat)
            }

            // вторая попытка добыть разрешение
            isShould -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Доступ к камере")
                    .setMessage("Нам нужна камера, чтобы сфотографировать котика")
                    .setNegativeButton("Отмена") { dialog, which ->
                        // грустно
                    }
                    .setPositiveButton("Дать доступ") { dialog, which ->
                        requestCameraPermission.launch(Manifest.permission.CAMERA)
                    }
                    .show()
            }

            // разрешения через настройки
            !isShould && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED -> {
                MaterialAlertDialogBuilder(this)
                    .setPositiveButton("Открыть настройки") { dialog, which ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    }
                    .show()
            }

            // еще не было запроса на доступ к камере
            else -> requestCameraPermission.launch(Manifest.permission.CAMERA)
        }
    }
}