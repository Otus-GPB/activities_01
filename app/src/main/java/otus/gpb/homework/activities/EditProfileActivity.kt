package otus.gpb.homework.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.Exception


class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var textName: TextView
    private lateinit var textSname: TextView
    private lateinit var textAge: TextView
    private lateinit var uriSave: Uri

    private val getImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { populateImage(it) }
        }

    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted: Boolean ->
            if (granted) {
                imageView
            } else if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_DENIED
            ) {
                MaterialAlertDialogBuilder(this)
                    .setPositiveButton("Открыть настройки") { _, _ ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    }
                    .show()
            }
        }

    private val startActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            object : ActivityResultCallback<ActivityResult?> {
                override fun onActivityResult(result: ActivityResult?) {
                    result ?: return
                    if (result.resultCode == Activity.RESULT_OK) {
                        Log.e("result", "yes")
                        fillForm(result.data)
                    }
                }
            })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        textName = findViewById(R.id.textview_name)
        textSname = findViewById(R.id.textview_surname)
        textAge = findViewById(R.id.textview_age)

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

        findViewById<Button>(R.id.button4).setOnClickListener {
            startActivityResult.launch(Intent(this, FillFormActivity::class.java))
        }
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        uriSave = uri
        imageView.setImageBitmap(bitmap)
    }

    private fun fillForm(data: Intent?) {
        textName.text = data?.getStringExtra(NAME)
        textSname.text = data?.getStringExtra(S_NAME)
        textAge.text = data?.getStringExtra(AGE)
    }

    private fun openSenderApp() {
        try {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                `package` = "org.telegram.messenger"
                putExtra(
                    Intent.EXTRA_TEXT,
                    "${textName.text} ${textSname.text} ${textAge.text}".trim()
                )
                putExtra(Intent.EXTRA_STREAM, uriSave)
                type = "text/plain"
            }
            startActivity(sendIntent)
        } catch (e: Exception) {
            Toast.makeText(this, "Не удалось сформировать сообщение", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestCameraPermission() {
        val isShould: Boolean =
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                imageView.setImageResource(R.drawable.cat)
            }

            isShould -> MaterialAlertDialogBuilder(this)
                .setTitle("Доступ к камере")
                .setMessage("Нам нужна камера, чтобы сфотографировать котика")
                .setNegativeButton("Отмена") { _, _ ->
                    // грустно
                }
                .setPositiveButton("Дать доступ") { _, _ ->
                    requestCameraPermission.launch(Manifest.permission.CAMERA)
                }
                .show()

            else -> {
                requestCameraPermission.launch(Manifest.permission.CAMERA)
            }
        }
    }
}