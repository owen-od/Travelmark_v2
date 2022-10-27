package ie.wit.travelmark.models

interface UserStore {
    fun login(username: String, password: String): Boolean
    fun findAllUsers(): List<UserModel>
    fun createUser(user: UserModel)
    fun delete(user: UserModel)
}