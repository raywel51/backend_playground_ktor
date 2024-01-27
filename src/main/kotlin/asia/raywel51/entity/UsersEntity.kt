package asia.raywel51.entity

import asia.raywel51.entity.customSerializer.LocalDateTimeSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import java.time.LocalDateTime

@Serializable
data class UsersEntity(
    @BsonId
    @Contextual
    val id: ObjectId? = null,

    @BsonProperty("username")
    val username: String,

    @BsonProperty("password")
    val password: String,

    @BsonProperty("email")
    val email: String,

    @BsonProperty("mobile_phone")
    val mobilePhone: String?,

    @BsonProperty("register_date")
    @Serializable(with = LocalDateTimeSerializer::class)
    val registerDate: LocalDateTime? = null
)
