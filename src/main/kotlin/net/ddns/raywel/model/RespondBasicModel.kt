package net.ddns.raywel.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RespondBasicModel(
    @SerialName("status") var status: Boolean,
    @SerialName("massage") var massage: String
)
