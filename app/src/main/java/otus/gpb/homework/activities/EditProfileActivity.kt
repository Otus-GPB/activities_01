package otus.gpb.homework.activities

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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import otus.gpb.homework.activities.FillFormActivity.Companion.FILL_FORM_USER_AGE_ARG
import otus.gpb.homework.activities.FillFormActivity.Companion.FILL_FORM_USER_NAME_ARG
import otus.gpb.homework.activities.FillFormActivity.Companion.FILL_FORM_USER_SURNAME_ARG

class EditProfileActivity : AppCompatActivity(R.layout.activity_edit_profile) {

    companion object {
        const val TYPE_IMAGE = "image/*"
        const val TYPE_PLAIN_TEXT = "text/plain"
        const val SENDER_APP_PACKAGE = "org.telegram.messenger"
    }

    private lateinit var imageView: ImageView
    private lateinit var userName: TextView
    private lateinit var userSurname: TextView
    private lateinit var userAge: TextView
    private var imageViewUri: Uri? = null

    private val fillFormLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    userName.text = it.getStringExtra(FILL_FORM_USER_NAME_ARG)
                    userSurname.text = it.getStringExtra(FILL_FORM_USER_SURNAME_ARG)
                    userAge.text = it.getStringExtra(FILL_FORM_USER_AGE_ARG)
                }
            }
        }

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let { populateImage(it) }
    }

    private val cameraPermissionRequestLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                takePhoto()
            } else if (!shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                showAppSettingsDialog()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageView = findViewById(R.id.imageview_photo)
        userName = findViewById(R.id.textview_name)
        userSurname = findViewById(R.id.textview_surname)
        userAge = findViewById(R.id.textview_age)

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

        imageView.setOnClickListener {
            takeProfileImageDialog()
        }

        findViewById<Button>(R.id.button4).apply {
            setOnClickListener {
                fillFormLauncher.launch(Intent(context, FillFormActivity::class.java))
            }
        }
    }

    private fun takePhoto() {
        imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cat))
    }

    private fun showAppSettingsDialog() {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(R.string.title_settings_dialog)
            setMessage(R.string.text_setting_dialog)
            setPositiveButton(R.string.button_settings) { dialog, _ ->
                startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName")))
                dialog.dismiss()
            }
            show()
        }
    }

    private fun showRationaleDialog(permission: String) {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(R.string.title_rationale_dialog)
            setMessage(R.string.text_rationale_dialog)
            setPositiveButton(R.string.button_ok) { dialog, _ ->
                cameraPermissionRequestLauncher.launch(permission)
                dialog.dismiss()
            }
            setNegativeButton(R.string.button_cancel) { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

    private fun getCameraPermission() {
        val permission = android.Manifest.permission.CAMERA
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            if (!shouldShowRequestPermissionRationale(permission)) {
                cameraPermissionRequestLauncher.launch(permission)
            } else {
                showRationaleDialog(permission)
            }
        } else {
            takePhoto()
        }
    }

    private fun takeProfileImageDialog() {
        val items = resources.getStringArray(R.array.array_image_chooser_item)
        MaterialAlertDialogBuilder(this).apply {
            setTitle(R.string.title_image_chooser)
            setItems(items) { dialog, which ->
                when (which) {
                    0 -> getCameraPermission()
                    else -> takePictureLauncher.launch(TYPE_IMAGE)
                }
                dialog.dismiss()
            }
            show()
        }
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
        imageViewUri = uri
    }

    private fun openSenderApp() {
        imageView.drawable
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = TYPE_PLAIN_TEXT
            setPackage(SENDER_APP_PACKAGE)
            putExtra(Intent.EXTRA_TEXT, "${userName.text}\n${userSurname.text}\n${userAge.text}")
            if (imageViewUri != null) {
                putExtra(Intent.EXTRA_STREAM, imageViewUri)
            }
        }

        if (intent.resolveActivity(packageManager) == null) {
            Toast.makeText(this, getString(R.string.toast_messeger_app_is_not_installed), Toast.LENGTH_SHORT).show()
        } else {
            startActivity(intent)
        }
    }
}
