package net.ddns.raywel.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestModel(
    @SerialName("username") val username: String? = null,
    @SerialName("password") val password: String? = null
)