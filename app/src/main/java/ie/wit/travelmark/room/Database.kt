package ie.wit.travelmark.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ie.wit.travelmark.helpers.Converters
import ie.wit.travelmark.models.TravelmarkModel

@Database(entities = arrayOf(TravelmarkModel::class), version = 1,  exportSchema = false)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {

    abstract fun travelmarkDao(): TravelmarkDao
}