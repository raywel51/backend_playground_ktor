package net.ddns.raywel.entity

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import java.time.LocalDateTime

@Serializable
data class TokenEntity(
    @BsonId
    @Contextual
    val id: ObjectId? = null,

    @BsonProperty("token")
    val token: String,

    @BsonProperty("expiry")
    @Contextual
    val expiry: LocalDateTime? = null,

    @BsonProperty("username")
    val username: String,
)
