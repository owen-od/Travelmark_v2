package ie.wit.travelmark.room

import androidx.room.*
import ie.wit.travelmark.models.TravelmarkModel

@Dao
interface TravelmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(travelmark: TravelmarkModel)

    @Query("SELECT * FROM TravelmarkModel")
    suspend fun findAll(): List<TravelmarkModel>

    @Query("select * from TravelmarkModel where id = :travelmarkId")
    suspend fun findById(travelmarkId: Long): TravelmarkModel

    @Query("select * from TravelmarkModel where category = :travelmarkCategory")
    suspend fun findByCategory(travelmarkCategory: String): List<TravelmarkModel>

    @Update
    suspend fun update(travelmark: TravelmarkModel)

    @Delete
    suspend fun deleteTravelmark(travelmark: TravelmarkModel)
}