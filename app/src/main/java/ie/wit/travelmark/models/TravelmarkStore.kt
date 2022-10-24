package ie.wit.travelmark.models

interface TravelmarkStore {
    fun findAll(): List<TravelmarkModel>
    fun create(travelmark: TravelmarkModel)
    fun update(travelmark: TravelmarkModel)
    fun delete(placemark: TravelmarkModel)
}