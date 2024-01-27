package asia.raywel51.repository

import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import asia.raywel51.entity.UsersEntity

class UserRepository(database: MongoDatabase) {
    private val users = database.getCollection<UsersEntity>("users")

    suspend fun findUserById(id: String): UsersEntity? {
        return users.find(eq("_id", id)).firstOrNull()
    }

    suspend fun findUserByUsername(username: String): UsersEntity? {
        return users.find(eq("username", username)).firstOrNull()
    }

    suspend fun findAllUsers(): List<UsersEntity> {
        return users.find().toList()
    }

    suspend fun insertUsers(usersEntity: UsersEntity) {
        users.insertOne(usersEntity)
    }

    suspend fun deleteUserByUsername(username: String): Boolean {
        val result = users.deleteOne(eq("username", username))
        return result.deletedCount > 0
    }

}