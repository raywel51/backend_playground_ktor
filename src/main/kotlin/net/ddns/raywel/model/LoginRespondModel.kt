package net.ddns.raywel.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRespondModel(
    @SerialName("status") val status: Boolean? = null,
    @SerialName("massage_th") val massageTH: String? = null,
    @SerialName("massage_en") val massageEN: String? = null,
    @SerialName("token") val token: String? = null
)
