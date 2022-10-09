package ie.wit.travelmark.models

import timber.log.Timber.i

class TravelmarkMemStore: TravelmarkStore {

    val travelmarks = ArrayList<TravelmarkModel>()

    override fun findAll(): List<TravelmarkModel> {
        return travelmarks
    }

    override fun create(travelmark: TravelmarkModel) {
        travelmarks.add(travelmark)
    }

    fun logAll() {
        travelmarks.forEach{ i("${it}") }
    }

}