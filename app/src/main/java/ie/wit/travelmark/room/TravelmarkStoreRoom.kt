package ie.wit.travelmark.room

import android.content.Context
import androidx.room.Room
import ie.wit.travelmark.models.TravelmarkModel
import ie.wit.travelmark.models.TravelmarkStore

class TravelmarkStoreRoom(val context: Context) : TravelmarkStore {

    var dao: TravelmarkDao

    init {
        val database = Room.databaseBuilder(context, Database::class.java, "room_sample.db")
            .fallbackToDestructiveMigration()
            .build()
        dao = database.travelmarkDao()
    }

    override suspend fun findAll(): List<TravelmarkModel> {
        return dao.findAll()
    }

    override suspend fun findTravelmarkById(travelmarkId: Long): TravelmarkModel? {
        return dao.findById(travelmarkId)
    }

    override suspend fun findTravelmarksByCategory(travelmarkCategory: String): List<TravelmarkModel> {
        if (travelmarkCategory == "All") {
            return dao.findAll()
        }
        return dao.findByCategory(travelmarkCategory)
    }

    override suspend fun create(travelmark: TravelmarkModel) {
        dao.create(travelmark)
    }

    override suspend fun update(travelmark: TravelmarkModel) {
        dao.update(travelmark)
    }

    override suspend fun delete(travelmark: TravelmarkModel) {
        dao.deleteTravelmark(travelmark)
    }

    fun clear() {
    }
}