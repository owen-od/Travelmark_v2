package ie.wit.travelmark.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TravelmarkModel(
    var id: Long = 0,
    var location: String = "",
    var title: String = "",
    var description: String = "") : Parcelable
