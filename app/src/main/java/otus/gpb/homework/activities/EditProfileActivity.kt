package otus.gpb.homework.activities

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var editProfile: Button
    private lateinit var person: Person
    private lateinit var name: TextView
    private lateinit var surName: TextView
    private lateinit var age: TextView
    private lateinit var uriForIntent: Uri


    private val launcherPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted: Boolean ->
            if (granted) {
                getPicture()
            } else if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                openSettingsDialog()
            }
        }

    private val getPhotoLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
            imageUri?.let {
                populateImage(it)
                uriForIntent = it
            }
        }

    private val gettingTextLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                person = result.data?.getParcelableExtra(FillFormActivity.EXTRA_OPTIONS) ?: Person(
                    "",
                    "",
                    ""
                )
                name.text = person.name
                surName.text = person.surName
                age.text = person.age

            } else {
                Toast.makeText(this, "Сведения не сохранялись", Toast.LENGTH_SHORT).show()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        editProfile = findViewById(R.id.button4)
        name = findViewById(R.id.textview_name)
        surName = findViewById(R.id.textview_surname)
        age = findViewById(R.id.textview_age)

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

            val items = arrayOf(getString(R.string.make_photo), getString(R.string.choose_photo))

            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.choose_method))
                .setItems(items) { _, which ->
                    when (which) {
                        0 -> checkPermissionWithRationale()
                        1 -> getPhotoLauncher.launch(MIME_TYPE)
                    }
                }
                .show()
        }
        editProfile.setOnClickListener {
            openFillFormActivity()
        }

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getPicture() {
        findViewById<ImageView>(R.id.imageview_photo).setImageDrawable(getDrawable(R.drawable.cat))
    }

    private fun checkPermissionWithRationale() {

        val permissionCheck = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )

        val requestRationale: Boolean = shouldShowRequestPermissionRationale(
            Manifest.permission.CAMERA
        )

        when {
            permissionCheck == PackageManager.PERMISSION_GRANTED -> getPicture()
            requestRationale -> showRationalDialog()

            else -> launcherPermission.launch(
                Manifest.permission.CAMERA
            )

        }


    }

    private fun showRationalDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.get_permission))
            .setMessage(getString(R.string.get_access_to_camera))
            .setNegativeButton(getString(R.string.denie_access)) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                launcherPermission.launch(Manifest.permission.CAMERA)

            }
            .show()
    }

    private fun openSettingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.open_settings))
            .setMessage(getString(R.string.open_settings_for_permissions))
            .setPositiveButton(getString(R.string.good)) { _, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            }
            .show()
    }


    private fun openFillFormActivity() {
        val intent = Intent(this, FillFormActivity::class.java)
        gettingTextLauncher.launch(intent)

    }


    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openSenderApp() {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = MIME_TYPE_INTENT
            setPackage("org.telegram.messenger")
            putExtra(
                Intent.EXTRA_TEXT,
                "Имя: ${person.name} \nФамилия: ${person.surName} \nВозраст: ${person.age}"
            )
            putExtra(Intent.EXTRA_STREAM, uriForIntent)
        }
        startActivity(sendIntent)
    }

    companion object {
        private const val MIME_TYPE_INTENT = "*/*"
        private const val MIME_TYPE = "image/*"
    }
}