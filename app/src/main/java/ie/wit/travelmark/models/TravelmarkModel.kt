package ie.wit.travelmark.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TravelmarkModel(
    var id: Long = 0,
    var image: Uri = Uri.EMPTY,
    var location: String = "",
    var title: String = "",
    var category: String = "N/A",
    var description: String = "") : Parcelable
