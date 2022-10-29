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
            foundTravelmark.lat = travelmark.lat
            foundTravelmark.lng = travelmark.lng
            foundTravelmark.zoom = travelmark.zoom
            foundTravelmark.rating = travelmark.rating
            logAll()
        }
    }

    override fun findTravelmarkById(travelmarkId: Long): TravelmarkModel? {
        var foundTravelmark: TravelmarkModel? = travelmarks.find { t -> t.id == travelmarkId }
        return foundTravelmark
    }

    override fun findTravelmarksByCategory(travelmarkCategory: String): List<TravelmarkModel> {
        var filteredlist: MutableList<TravelmarkModel>
        var travelmarks = findAll().toMutableList()

        when (travelmarkCategory) {
            "all" -> {
                filteredlist = travelmarks
            }
            "Thing to do" -> {
                filteredlist = travelmarks.filter { it.category == "Thing to do" } as MutableList<TravelmarkModel>
            }

            "Sight to see" -> {
                filteredlist = travelmarks.filter { it.category == "Sight to see" } as MutableList<TravelmarkModel>
            }

            "Food to eat" -> {
                filteredlist = travelmarks.filter { it.category == "Food to eat" } as MutableList<TravelmarkModel>
            }
            else -> {
                filteredlist = travelmarks
            }
        }
        return filteredlist
    }

    override fun delete(travelmark: TravelmarkModel) {
        travelmarks.remove(travelmark)
    }

    fun logAll() {
        travelmarks.forEach{ i("${it}") }
    }

}