package otus.gpb.homework.activities

import android.Manifest.permission.CAMERA
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
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var textViewName: TextView
    private lateinit var textViewSurname: TextView
    private lateinit var textViewAge: TextView

    private var imageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                setCatImage()
            } else if (!shouldShowRequestPermissionRationale(CAMERA)) {
                showOpenSettingsDialog()
            }
        }

    private val getImage = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            imageUri = uri
            populateImage(uri)
        }
    }

    private val getUserData = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val name = result.data?.getStringExtra(NAME) ?: ""
            val surname = result.data?.getStringExtra(SURNAME) ?: ""
            val age = result.data?.getStringExtra(AGE) ?: ""
            updateUserData(name = name, surname = surname, age = age)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        imageView = findViewById(R.id.imageview_photo)
        textViewName = findViewById(R.id.textview_name)
        textViewSurname = findViewById(R.id.textview_surname)
        textViewAge = findViewById(R.id.textview_age)

        imageView.setOnClickListener {
            showPhotoActionDialog()
        }

        findViewById<Button>(R.id.button_edit).setOnClickListener {
            getUserData.launch(Intent(this, FillFormActivity::class.java))
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

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> setCatImage()
            shouldShowRequestPermissionRationale(CAMERA) -> showCameraPermissionDialog()
            else -> requestPermissionLauncher.launch(CAMERA)
        }
    }

    private fun showPhotoActionDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.select_action))
            .setItems(
                arrayOf(
                    getString(R.string.make_photo),
                    getString(R.string.select_photo)
                )
            ) { _, which ->
                when (which) {
                    0 -> requestCameraPermission()
                    1 -> getImage.launch(
                        PickVisualMediaRequest(
                            PickVisualMedia.ImageOnly
                        )
                    )
                }
            }
            .show()
    }

    private fun showCameraPermissionDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.require_camera_access))
            .setMessage(getString(R.string.camera_permission_rational_message))
            .setPositiveButton(getString(R.string.provide_access)) { _, _ ->
                requestPermissionLauncher.launch(CAMERA)
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showOpenSettingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.require_camera_access))
            .setMessage(getString(R.string.camera_open_settings_message))
            .setPositiveButton(getString(R.string.open_settings)) { _, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
                startActivity(intent)
            }
            .show()
    }

    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun setCatImage() {
        imageUri = null
        imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cat))
    }

    private fun updateUserData(name: String, surname: String, age: String) {
        textViewName.text = name
        textViewSurname.text = surname
        textViewAge.text = age
    }

    private fun openSenderApp() {
        val text = "${textViewName.text}\n${textViewSurname.text}\n${textViewAge.text}"
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                setPackage(TELEGRAM_PACKAGE)
                type = "image/*"
                putExtra(Intent.EXTRA_TEXT, text)
                putExtra(Intent.EXTRA_STREAM, imageUri)
            }
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.telegram_not_installed), Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        const val NAME = "name"
        const val SURNAME = "surname"
        const val AGE = "age"
        const val TELEGRAM_PACKAGE = "org.telegram.messenger"
    }

}
