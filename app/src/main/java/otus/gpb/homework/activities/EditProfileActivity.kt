package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {
    companion object {
        private const val EDIT_ACTIVITY_RESULT_IMAGE = "result_image"
    }

    private var camera_permission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            when {
                it -> setAvatarbyCat()
                else ->
                    when {
                        !ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.CAMERA
                        )
                        -> {
                            opensettingsApp()
                        }
                    }
            }
        }
    private val gallery_getphoto = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { it ->
            populateImage(it)
        }
    }


    private lateinit var imageView: ImageView
    private lateinit var imageUser: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        imageUser = (savedInstanceState?.getParcelable(EDIT_ACTIVITY_RESULT_IMAGE) ?: Uri.parse(
            "android.resource://"
                    + getPackageName() + "/" + R.drawable.ic_baseline_add_photo_alternate_24
        )) as Uri
        if (imageUser != Uri.parse(
                "android.resource://"
                        + getPackageName() + "/" + R.drawable.ic_baseline_add_photo_alternate_24
            )
        ) {
            imageView.setImageURI(imageUser)
            imageView.tag = imageUser
        }



        imageView.setOnClickListener {
            chooseAvatar()
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
     * Save profile image
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (imageUser != Uri.parse(
                "android.resource://"
                        + getPackageName() + "/" + R.drawable.ic_baseline_add_photo_alternate_24
            )
        )
            outState.putParcelable(EDIT_ACTIVITY_RESULT_IMAGE, imageUser)
    }

    /**
     * add image in avatar from resource
     */
    private fun setAvatarbyCat() {
        imageView.setImageResource(R.drawable.cat)
        val imageUri = Uri.parse(
            "android.resource://"
                    + getPackageName() + "/" + R.drawable.cat
        );
        imageView.tag = imageUri
        imageUser = imageUri

    }

    /**
     * open dialog when user can choose action - create photo or open gallery
     */
    private fun chooseAvatar() {
        val choose: Array<String> = resources.getStringArray(R.array.message_dialogChooseAvatar)
        MaterialAlertDialogBuilder(this).apply {
            setTitle(resources.getString(R.string.title_dialogChooseAvatar))
            setItems(choose, { dialog, which ->
                when (which) {
                    0 -> clickOnCamera()
                    1 -> gallery_getphoto.launch("image/*")
                }
            })
            show()
        }
    }

    /**
     * method asks permissions for using Camera
     */
    private fun clickOnCamera() {
        if (ActivityCompat.checkSelfPermission(
                this@EditProfileActivity, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            setAvatarbyCat()
        }
        when {
            !ActivityCompat.shouldShowRequestPermissionRationale(
                this@EditProfileActivity,
                Manifest.permission.CAMERA
            ) -> camera_permission.launch(Manifest.permission.CAMERA)

            else -> showdialogForPermission()
        }
    }

    /**
     * if user didn't give Camera permissions  to app once time
     * app shows dialog where tells why we need to get this permissions
     */
    private fun showdialogForPermission() {
        MaterialAlertDialogBuilder(this@EditProfileActivity)
            .setTitle("Почему нам нужна камера:")
            .setMessage(
                "Без камеры мы не можем сделать\n" +
                        "классные фоточки и запостить их на аватар"
            )
            .setPositiveButton("Ok!") { d, _ ->
                camera_permission.launch(Manifest.permission.CAMERA)
                d.dismiss()
            }
            .setNegativeButton("Отмена") { d, _ ->
                d.dismiss()

            }
            .show()
    }

    /**
     * if user chose 'don't ask again' when app asked permissions for Camera
     * app shows dialog with an advice go to app's settings and give permission to our app
     */
    private fun opensettingsApp() {
        val intent1 = Intent()
        intent1.data = Uri.fromParts("package", packageName, null)
        intent1.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        if (packageManager.resolveActivity(intent1, PackageManager.MATCH_DEFAULT_ONLY) == null) {
            Toast.makeText(this, "Доступ запрещен навсегда", Toast.LENGTH_LONG).show()
        } else {
            MaterialAlertDialogBuilder(this@EditProfileActivity)
                .setTitle("Доступ к камере запрещен!")
                .setMessage("Открыть доступ можно в настройках приложения")
                .setPositiveButton("Перейти в настройки...") { d, _ ->
                    startActivity(intent1)
                    d.dismiss()
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
        imageView.tag = uri
        imageUser = uri
    }

    private fun openSenderApp() {
        TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")
    }
}