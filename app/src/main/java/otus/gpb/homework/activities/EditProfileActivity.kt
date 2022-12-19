package otus.gpb.homework.activities

import android.Manifest.permission.CAMERA
import android.content.ActivityNotFoundException
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

const val TELEGRAM_MANAGER = "org.telegram.messenger"

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var name: TextView
    private lateinit var surname: TextView
    private lateinit var age: TextView
    private var imageUri: Uri? = null

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

        name = findViewById(R.id.textview_name)
        surname = findViewById(R.id.textview_surname)
        age = findViewById(R.id.textview_age)

        imageView.setOnClickListener {
            selectActionDialog()
        }

        findViewById<Button>(R.id.button4).setOnClickListener {
            activityLauncher.launch(Intent(this, FillFormActivity::class.java))
        }


    }

    private fun openCatPhoto() {
        imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cat))
    }

    private fun showSettingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.settings))
            .setMessage(resources.getString(R.string.settings_message))
            .setPositiveButton(resources.getString(R.string.open_settings)) { dialog, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null))
                startActivity(intent)
                dialog.dismiss()
            }

            .show()
    }

    private val activityLauncher= registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        result -> if (result.resultCode == RESULT_OK) {
            result.data?.let {
                name.text = it.getStringExtra(USER_NAME)
                surname.text = it.getStringExtra(USER_SURNAME)
                age.text = it.getStringExtra(USER_AGE)
            }
        }
    }

    private val cameraLauncher= registerForActivityResult(
        ActivityResultContracts.RequestPermission()) {
            granted ->
        if (granted) {
            openCatPhoto()
        } else if (!shouldShowRequestPermissionRationale(CAMERA)) {
            showSettingsDialog()
        }
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        uri: Uri? -> uri?.let{ populateImage(it) }
    }

    private fun showRationaleDialog() {
           MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.rationale_title))
            .setMessage(resources.getString(R.string.rationale_message))
            .setPositiveButton(resources.getString(R.string.open_access)) {dialog, _ ->
                cameraLauncher.launch(CAMERA)
                dialog.dismiss()
            }
            .setNegativeButton(resources.getString(R.string.cancel)) {dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun getPermission() {
        if (ContextCompat.checkSelfPermission(this, CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCatPhoto()
        } else {
            if (shouldShowRequestPermissionRationale(CAMERA)) {
                showRationaleDialog()
            } else {
                cameraLauncher.launch(CAMERA)
            }
        }
    }


    private fun getPhotoFromGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun selectActionDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.title))
            .setItems(
                arrayOf(
                    getString(R.string.take_photo),
                    getString(R.string.select_photo)
                )
            ) {_, which ->
                when (which) {
                    0 -> getPermission()
                    1 -> getPhotoFromGallery()
                }
            }
            .show()
    }



    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
        imageUri = uri
    }

    private fun openSenderApp() {
       // TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")
        try {
            val intentSend = Intent(Intent.ACTION_SEND)
            intentSend.type = "text/plain"
            intentSend.putExtra(Intent.EXTRA_TEXT, "${name.text}, ${surname.text}, ${age.text}")
            intentSend.setPackage(TELEGRAM_MANAGER)
            intentSend.putExtra(Intent.EXTRA_STREAM, imageUri)
            startActivity(intentSend)
        } catch(exception: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.telegram), Toast.LENGTH_SHORT).show()
        }
    }
}