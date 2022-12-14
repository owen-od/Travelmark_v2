package ie.wit.travelmark.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class TravelmarkFireStore(val context: android.content.Context) : TravelmarkStore {
    val travelmarks = ArrayList<TravelmarkModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference

    override suspend fun findAll(): List<TravelmarkModel> {
        return travelmarks
    }

    override suspend fun findTravelmarkById(id: Long): TravelmarkModel? {
        val foundTravelmark: TravelmarkModel? = travelmarks.find { p -> p.id == id }
        return foundTravelmark
    }

    override suspend fun findTravelmarksByCategory(travelmarkCategory: String): List<TravelmarkModel> {
        var filteredlist: MutableList<TravelmarkModel>
        var travelmarks = findAll().toMutableList()

        when (travelmarkCategory) {
            "All" -> {
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

    override suspend fun create(travelmark: TravelmarkModel) {
        val key = db.child("users").child(userId).child("travelmarks").push().key
        key?.let {
            travelmark.fbId = key
            travelmarks.add(travelmark)
            db.child("users").child(userId).child("travelmarks").child(key).setValue(travelmark)
        }
    }

    override suspend fun update(travelmark: TravelmarkModel) {
        var foundTravelmark: TravelmarkModel? = travelmarks.find { p -> p.fbId == travelmark.fbId }
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
        }

        db.child("users").child(userId).child("travelmarks").child(travelmark.fbId).setValue(travelmark)

    }

    override suspend fun delete(travelmark: TravelmarkModel) {
        db.child("users").child(userId).child("travelmarks").child(travelmark.fbId).removeValue()
        travelmarks.remove(travelmark)
    }

    override suspend fun clear() {
        travelmarks.clear()
    }

    fun fetchTravelmarks(travelmarksReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot!!.children.mapNotNullTo(travelmarks) {
                    it.getValue<TravelmarkModel>(
                        TravelmarkModel::class.java
                    )
                }
                travelmarksReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        travelmarks.clear()
        db.child("users").child(userId).child("travelmarks")
            .addListenerForSingleValueEvent(valueEventListener)
    }
}