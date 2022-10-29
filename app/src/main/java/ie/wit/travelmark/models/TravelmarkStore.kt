package ie.wit.travelmark.models

interface TravelmarkStore {
    fun findAll(): List<TravelmarkModel>
    fun create(travelmark: TravelmarkModel)
    fun update(travelmark: TravelmarkModel)
    fun delete(travelmark: TravelmarkModel)
    fun findTravelmarkById(travelmarkId: Long): TravelmarkModel?
    fun findTravelmarksByCategory(travelmarkCategory: String): List<TravelmarkModel>
}