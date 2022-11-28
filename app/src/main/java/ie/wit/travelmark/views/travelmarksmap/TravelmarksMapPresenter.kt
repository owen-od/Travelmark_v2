package ie.wit.travelmark.views.travelmarksmap

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ie.wit.travelmark.main.MainApp

class TravelmarksMapPresenter (val view: TravelmarksMapView) {

    var app: MainApp

    init{
        app = view.application as MainApp
    }

    suspend fun doPopulateMap(map: GoogleMap) {
        map.setOnMarkerClickListener(view)
        map.uiSettings.setZoomControlsEnabled(true)
        // var customMarker = BitmapDescriptorFactory.fromResource(R.drawable....)
        var colourMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
        app.travelmarks.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc).icon(colourMarker)
            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    suspend fun doMarkerSelected(marker: Marker) {
        val tag = marker.tag as Long
        val travelmark = app.travelmarks.findTravelmarkById(tag)
        if (travelmark != null) view.showTravelmark(travelmark)
    }

    fun doHome() {
        view.finish()
    }
}