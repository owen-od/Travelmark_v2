package ie.wit.travelmark.models

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import ie.wit.travelmark.helpers.readImageFromPath
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class TravelmarkFireStore(val context: android.content.Context) : TravelmarkStore {
    val travelmarks = ArrayList<TravelmarkModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference

    override suspend fun findAll(): List<TravelmarkModel> {
        return travelmarks
    }

    fun generateRandomId(): Long {
        return Random().nextLong()
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
            travelmark.id = generateRandomId() //note that this is also needed as used for marker on map of all placemarks
            travelmarks.add(travelmark)
            db.child("users").child(userId).child("travelmarks").child(key).setValue(travelmark)
            updateImage(travelmark)
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
        if(travelmark.image.length > 0){
            updateImage(travelmark)
        }

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
        st = FirebaseStorage.getInstance().reference
        travelmarks.clear()
        db.child("users").child(userId).child("travelmarks")
            .addListenerForSingleValueEvent(valueEventListener)
    }

    fun updateImage(travelmark: TravelmarkModel) {
        if (travelmark.image != "") {
            val fileName = File(travelmark.image)
            val imageName = fileName.getName()

            var imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, travelmark.image)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        travelmark.image = it.toString()
                        db.child("users").child(userId).child("travelmarks").child(travelmark.fbId).setValue(travelmark)
                    }
                }
            }
        }
    }
}