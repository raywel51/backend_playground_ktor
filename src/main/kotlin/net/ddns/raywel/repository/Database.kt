package net.ddns.raywel.repository

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.github.cdimascio.dotenv.dotenv

object Database {
    private val dotenv = dotenv()

    private val mongoDbName: String = dotenv["MONGO_DB_NAME"] ?: ""
    private val mongoUser: String = dotenv["MONGO_USER"] ?: ""
    private val mongoPassword: String = dotenv["MONGO_PASSWORD"] ?: ""
    private val mongoHost: String = dotenv["MONGO_HOST"] ?: ""
    private val mongoPort: String = dotenv["MONGO_PORT"] ?: ""

    private val client: MongoClient by lazy {
        val connectionString = "mongodb://$mongoUser:$mongoPassword@$mongoHost:$mongoPort"
        MongoClient.create(connectionString)
    }

    private val database: MongoDatabase
        get() = client.getDatabase(mongoDbName)

    val userRepository: UserRepository by lazy { UserRepository(database) }
    val tokenRepository: TokenRepository by lazy { TokenRepository(database) }
}
