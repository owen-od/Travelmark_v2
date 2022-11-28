package ie.wit.travelmark.models

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class TravelmarkModel(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                           var image: Uri = Uri.EMPTY,
                           var location: String = "",
                           var title: String = "",
                           var category: String = "N/A",
                           var description: String = "",
                           var lat: Double = 0.0,
                           var lng: Double = 0.0,
                           var zoom: Float = 0f,
                           var rating: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable
