package otus.gpb.homework.activities

import android.Manifest
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
import androidx.core.app.ActivityCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {
    companion object {
        private const val EDIT_ACTIVITY_RESULT_USER = "result_user"
        private const val EDIT_ACTIVITY_RESULT_IMAGE = "result_image"
    }

    private var cameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            when {
                true -> setAvatarbyCat()
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
    private val galleryGetphoto = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { populateImage(it) }
    }
    private val changeprofileActivity =
        registerForActivityResult(FillFormActivity.FillFormContract()) {
            if (it != null) {
                userform = it
                changedProfile()
            } else Toast.makeText(
                this,
                "Что-то не получается.\nПопробуйте еще раз!",
                Toast.LENGTH_LONG
            ).show()
        }
    private lateinit var imageView: ImageView
    private lateinit var imageUser: Uri
    private lateinit var userform: FillFormActivity.UserFormData
    private lateinit var changeprofile: Button
    private lateinit var textFirstname: TextView
    private lateinit var textSecondname: TextView
    private lateinit var textage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        imageUser = (
            savedInstanceState?.getParcelable(EDIT_ACTIVITY_RESULT_IMAGE)
                ?: Uri.parse(
                    "android.resource://" + getPackageName() + "/" + R.drawable.ic_baseline_add_photo_alternate_24
                )
            ) as Uri

        userform = savedInstanceState?.getParcelable(EDIT_ACTIVITY_RESULT_USER)
            ?: FillFormActivity.UserFormData.USER_DEFAULT

        imageView = findViewById(R.id.imageview_photo)

        if (imageUser != Uri.parse(
                "android.resource://" + getPackageName() + "/" + R.drawable.ic_baseline_add_photo_alternate_24
            )
        ) {
            imageView.setImageURI(imageUser)
            imageView.tag = imageUser
        }

        textFirstname = findViewById(R.id.textview_name)
        textSecondname = findViewById(R.id.textview_surname)
        textage = findViewById(R.id.textview_age)
        if (userform.realCreate == true) {
            textFirstname.text = (userform.firstname.toString())
            textSecondname.text = (userform.secondname.toString())
            textage.text = (userform.age.toString())
        }

        imageView.setOnClickListener {
            chooseAvatar()
        }
        changeprofile = findViewById(R.id.button4)
        changeprofile.setOnClickListener {
            changeprofileActivity.launch(userform)
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
     * Save profile image and information about user
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(EDIT_ACTIVITY_RESULT_USER, userform)
        if (imageUser != Uri.parse(
                "android.resource://" + getPackageName() + "/" + R.drawable.ic_baseline_add_photo_alternate_24
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
            "android.resource://" + getPackageName() + "/" + R.drawable.cat
        )
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
                    1 -> galleryGetphoto.launch("image/*")
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
            ) -> cameraPermission.launch(Manifest.permission.CAMERA)

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
                "Без камеры мы не можем сделать\n" + "классные фоточки и запостить их на аватар")
            .setPositiveButton("Ok!") { d, _ ->
                cameraPermission.launch(Manifest.permission.CAMERA)
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
    /**
     * Changed user profile by texts that we got from FillFormActivity
     */
    private fun changedProfile() {
        findViewById<TextView>(R.id.textview_name).text = userform.firstname
        findViewById<TextView>(R.id.textview_surname).text = userform.secondname
        findViewById<TextView>(R.id.textview_age).text = userform.age.toString()
    }

    /**
     * create intent for launch Telegram app and give it information about user (image and text)
     */
    private fun openSenderApp() {
        if (imageUser == Uri.parse(
                "android.resource://" + getPackageName() + "/" + R.drawable.ic_baseline_add_photo_alternate_24
            )
        ) {
            Toast.makeText(this, "Oops, your profile image is empty...", Toast.LENGTH_LONG).show()
        } else if (userform.realCreate == false) {
            Toast.makeText(this, "Oops, your account is empty...", Toast.LENGTH_LONG).show()
        } else {
            val imgAdress = imageView.tag.toString()
            val imageUri = Uri.parse(imgAdress)
            val telegramIntent = Intent(Intent.ACTION_SEND)
            telegramIntent.setPackage("org.telegram.messenger")
            telegramIntent.setType("text/plain")
            telegramIntent.setType("image/*")
            telegramIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
            telegramIntent.putExtra(Intent.EXTRA_TEXT, userform.toString())
            if (packageManager.resolveActivity(
                    telegramIntent,
                    PackageManager.MATCH_DEFAULT_ONLY
                ) == null
            ) {
                Toast.makeText(this, "Oops, you don't have Telegram...", Toast.LENGTH_LONG).show()
            } else {
                startActivity(telegramIntent)
            }
        }
    }
}