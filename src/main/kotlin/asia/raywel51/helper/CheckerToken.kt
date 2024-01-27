package asia.raywel51.helper

import asia.raywel51.model.RespondBasicModel
import asia.raywel51.repository.Database
import java.time.LocalDateTime

object CheckerToken {
    suspend fun checkToken(token: String?): RespondBasicModel {
        token?.let {
            val checkToken = Database.tokenRepository.findTokenByToken(it)

            checkToken?.let { tokenEntity ->

                val currentDateTime = LocalDateTime.now()
                val isAfter = tokenEntity.expiry?.isAfter(currentDateTime)

                if (isAfter == true) {
                    return RespondBasicModel(
                        status = true,
                        message = ""
                    )
                } else {
                    return RespondBasicModel(
                        status = false,
                        message = "token is expiry"
                    )
                }


            }?: run {
                return RespondBasicModel(
                    status = false,
                    message = "not found token in system"
                )
            }

        } ?: run {
            return RespondBasicModel(
                status = false,
                message = "Token Is Null"
            )
        }
    }
}