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

    override fun findAll(): MutableList<TravelmarkModel> {
        logAll()
        return travelmarks
    }

    override fun create(travelmark: TravelmarkModel) {
        travelmark.id = generateRandomId()
        travelmarks.add(travelmark)
        serialize()
    }


    override fun update(travelmark: TravelmarkModel) {
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
        }
    }

    override fun findTravelmarkById(travelmarkId: Long): TravelmarkModel? {
        var foundTravelmark: TravelmarkModel? = travelmarks.find { t -> t.id == travelmarkId }
        return foundTravelmark
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(travelmarks, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        travelmarks = gsonBuilder.fromJson(jsonString, listType)
    }

    override fun delete(travelmark: TravelmarkModel) {
        travelmarks.remove(travelmark)
        serialize()
    }

    private fun logAll() {
        travelmarks.forEach { Timber.i("$it") }
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