package ie.wit.travelmark.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.wit.travelmark.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "travelmarks.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<TravelmarkModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class TravelmarkJSONStore(private val context: Context) : TravelmarkStore {

    var travelmarks = mutableListOf<TravelmarkModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override suspend fun findAll(): MutableList<TravelmarkModel> {
        logAll()
        return travelmarks
    }

    override suspend fun create(travelmark: TravelmarkModel) {
        travelmark.id = generateRandomId()
        travelmarks.add(travelmark)
        serialize()
    }


    override suspend fun update(travelmark: TravelmarkModel) {
        var foundTravelmark: TravelmarkModel? = travelmarks.find { t -> t.id == travelmark.id }
        if (foundTravelmark != null) {
            foundTravelmark.title = travelmark.title
            foundTravelmark.description = travelmark.description
            foundTravelmark.location = travelmark.location
            foundTravelmark.image = travelmark.image
            foundTravelmark.category = travelmark.category
            foundTravelmark.lat = travelmark.lat
            foundTravelmark.lng = travelmark.lng
            foundTravelmark.zoom = travelmark.zoom
            foundTravelmark.rating = travelmark.rating
            foundTravelmark.favourite = travelmark.favourite
            serialize()
        }
    }

    override suspend fun findTravelmarkById(travelmarkId: Long): TravelmarkModel? {
        var foundTravelmark: TravelmarkModel? = travelmarks.find { t -> t.id == travelmarkId }
        return foundTravelmark
    }

    override suspend fun findTravelmarksByCategory(travelmarkCategory: String): List<TravelmarkModel> {
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
            "Favourite" -> {
                filteredlist = travelmarks.filter { it.favourite } as MutableList<TravelmarkModel>
            }
            else -> {
                filteredlist = travelmarks
            }
        }
        return filteredlist
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(travelmarks, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        travelmarks = gsonBuilder.fromJson(jsonString, listType)
    }

    override suspend fun delete(travelmark: TravelmarkModel) {
        travelmarks.remove(travelmark)
        serialize()
    }

    private fun logAll() {
        travelmarks.forEach { Timber.i("$it") }
    }

    override suspend fun clear(){
        travelmarks.clear()
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}