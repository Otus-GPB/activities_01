package otus.gpb.homework.activities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(val name: String, val surName: String, val age: String) : Parcelable
