package ie.wit.travelmark.models

interface TravelmarkStore {
    fun findAll(): List<TravelmarkModel>
    fun create(travelmark: TravelmarkModel)
}