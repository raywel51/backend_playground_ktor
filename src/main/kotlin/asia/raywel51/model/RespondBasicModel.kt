package asia.raywel51.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RespondBasicModel(
    @SerialName("status") var status: Boolean,
    @SerialName("message") var message: String
)
