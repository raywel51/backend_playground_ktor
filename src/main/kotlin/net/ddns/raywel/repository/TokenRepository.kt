package net.ddns.raywel.repository

import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import net.ddns.raywel.entity.TokenEntity
import net.ddns.raywel.entity.UsersEntity

class TokenRepository(database: MongoDatabase) {
    private val tokens = database.getCollection<TokenEntity>("token")

    suspend fun findUserById(id: String): TokenEntity? {
        return tokens.find(eq("_id", id)).firstOrNull()
    }
}