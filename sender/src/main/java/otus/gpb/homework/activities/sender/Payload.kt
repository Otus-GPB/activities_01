package otus.gpb.homework.activities.sender

import android.os.Parcel
import android.os.Parcelable


data class Payload(
    val title: String,
    val year: String,
    val description: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "Default not found",
        parcel.readString() ?: "0000",
        parcel.readString() ?: "Description not found"
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(year)
        parcel.writeString(description)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Payload> {
        override fun createFromParcel(parcel: Parcel): Payload {
            return Payload(parcel)
        }

        override fun newArray(size: Int): Array<Payload?> {
            return arrayOfNulls(size)
        }
    }

}