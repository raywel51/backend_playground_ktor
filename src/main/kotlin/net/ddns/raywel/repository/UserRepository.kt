package net.ddns.raywel.repository

import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import net.ddns.raywel.entity.UsersEntity

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
}