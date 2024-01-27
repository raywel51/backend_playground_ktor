package asia.raywel51.repository

import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import asia.raywel51.entity.TokenEntity
import asia.raywel51.entity.UsersEntity
import java.util.regex.Pattern

class TokenRepository(database: MongoDatabase) {
    private val tokens = database.getCollection<TokenEntity>("token")

    suspend fun findTokenByToken(token: String): TokenEntity? {
        val regexPattern = Pattern.compile("^$token$", Pattern.CASE_INSENSITIVE)
        return tokens.find(eq("token", regexPattern)).firstOrNull()
    }

    suspend fun insertToken(tokenEntity: TokenEntity) {
        tokens.insertOne(tokenEntity)
    }
}