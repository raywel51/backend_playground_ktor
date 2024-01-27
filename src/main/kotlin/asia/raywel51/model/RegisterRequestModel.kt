package asia.raywel51.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestModel(
    @SerialName("username") val username: String? = null,
    @SerialName("password") val password: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("mobile_phone") val mobilePhone: String? = null,
    @SerialName("real_name") val realName: String? = null,
    @SerialName("family_name") val familyName: String? = null
)