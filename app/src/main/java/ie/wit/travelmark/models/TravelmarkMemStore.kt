package ie.wit.travelmark.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class TravelmarkMemStore: TravelmarkStore {

    val travelmarks = ArrayList<TravelmarkModel>()

    override fun findAll(): List<TravelmarkModel> {
        return travelmarks
    }

    override fun create(travelmark: TravelmarkModel) {
        travelmark.id = getId()
        travelmarks.add(travelmark)
        logAll()
    }

    override fun update(travelmark: TravelmarkModel) {
        var foundTravelmark: TravelmarkModel? = travelmarks.find { t -> t.id == travelmark.id }
        if (foundTravelmark != null) {
            foundTravelmark.title = travelmark.title
            foundTravelmark. description = travelmark.description
            foundTravelmark.location = travelmark.location
            foundTravelmark.image = travelmark.image
            foundTravelmark.category = travelmark.category
            logAll()
        }
    }

    fun logAll() {
        travelmarks.forEach{ i("${it}") }
    }

}