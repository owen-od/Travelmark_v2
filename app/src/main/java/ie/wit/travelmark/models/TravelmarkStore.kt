package ie.wit.travelmark.models

interface TravelmarkStore {
    suspend fun findAll(): List<TravelmarkModel>
    suspend fun create(travelmark: TravelmarkModel)
    suspend fun update(travelmark: TravelmarkModel)
    suspend fun delete(travelmark: TravelmarkModel)
    suspend fun findTravelmarkById(travelmarkId: Long): TravelmarkModel?
    suspend fun findTravelmarksByCategory(travelmarkCategory: String): List<TravelmarkModel>
}