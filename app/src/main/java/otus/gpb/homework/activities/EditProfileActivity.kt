package otus.gpb.homework.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import otus.gpb.homework.activities.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    companion object {
        const val cameraPermission = android.Manifest.permission.CAMERA
    }

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                takePhoto()
            } else if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    cameraPermission
                )
            ) {
                showSettingsDialog()
            }
        }

    private val choosePhotoLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let { populateImage(it) }
        }

    private val fillFormLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    binding.apply {
                        textviewName.text = it.getStringExtra(EXTRA_NAME_KEY)
                        textviewSurname.text = it.getStringExtra(EXTRA_SURNAME_KEY)
                        textviewAge.text = it.getStringExtra(EXTRA_AGE_KEY)
                    }
                }
            }
        }

    private var photoUri: Uri? = null
    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageviewPhoto.setOnClickListener {
            imageviewPhotoListener()
        }

        binding.toolbar.apply {
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

        binding.buttonEditProfile.setOnClickListener {
            fillFormLauncher.launch(Intent(this, FillFormActivity::class.java))
        }
    }

    private fun imageviewPhotoListener() {
        val photoDialogItems = resources.getStringArray(R.array.dialog_photo_items)

        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_photo_source_title)
            .setItems(photoDialogItems) { _, which ->
                when (which) {
                    0 -> requestCameraPermissionAndThenTakePhoto()
                    else -> choosePhoto()
                }
            }
            .show()
    }

    private fun requestCameraPermissionAndThenTakePhoto() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                cameraPermission
            ) == PackageManager.PERMISSION_GRANTED -> {
                takePhoto()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(this, cameraPermission) -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.dialog_photo_rationale_title)
                    .setMessage(R.string.dialog_photo_rationale_message)
                    .setPositiveButton(R.string.dialog_photo_rationale_ok) { _, _ ->
                        requestCameraPermission()
                    }
                    .setNegativeButton(R.string.dialog_photo_rationale_cancel) { dialog, _ ->
                        dialog.cancel()
                    }
                    .show()
            }

            else -> requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        Log.d("EPA", "inside requestCameraPermission()")
        cameraPermissionLauncher.launch(cameraPermission)
    }

    private fun showSettingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_settings_title)
            .setPositiveButton(R.string.dialog_settings_open) { _, _ ->
                startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:$packageName")
                    )
                )
            }
            .show()
    }

    private fun takePhoto() {
        photoUri = null
        binding.imageviewPhoto.setImageResource(R.drawable.cat)
    }

    private fun choosePhoto() {
        choosePhotoLauncher.launch(IMAGE_MEDIA_TYPE)
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        photoUri = uri
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        binding.imageviewPhoto.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        val profileInfo =
            "${binding.textviewName.text} ${binding.textviewSurname.text} ${binding.textviewAge.text}".trim()

        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            `package` = "org.telegram.messenger"
            putExtra(Intent.EXTRA_TEXT, profileInfo)
            putExtra(Intent.EXTRA_STREAM, photoUri)
            type = IMAGE_MEDIA_TYPE
        }
        startActivity(Intent.createChooser(shareIntent, null))
    }
}