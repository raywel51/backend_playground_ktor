package asia.raywel51.handler.v1.users

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import asia.raywel51.entity.TokenEntity
import asia.raywel51.entity.UsersEntity
import asia.raywel51.helper.ConvertMobilePhone
import asia.raywel51.helper.EncryptHelper
import asia.raywel51.helper.GenerateBearerToken
import asia.raywel51.model.LoginRequestModel
import asia.raywel51.model.LoginRespondModel
import asia.raywel51.model.RegisterRequestModel
import asia.raywel51.model.RespondBasicModel
import asia.raywel51.repository.Database
import org.bson.types.ObjectId
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

class CredentialHandler {
    suspend fun userLogin(c: ApplicationCall) {
        try {
            val loginRequest = c.receive<LoginRequestModel>()

            if (loginRequest.username.isNullOrBlank()) {
                return c.respond(
                    status = HttpStatusCode.BadRequest,
                    message = RespondBasicModel(
                        status = false,
                        message = "Username required"
                    )
                )
            }

            if (loginRequest.password.isNullOrBlank()) {
                return c.respond(
                    status = HttpStatusCode.BadRequest,
                    message = RespondBasicModel(
                        status = false,
                        message = "Password are required"
                    )
                )
            }

            val usersEntity: UsersEntity? = Database.userRepository.findUserByUsername(loginRequest.username)

            usersEntity?.let {

                val checkPassword = it.password == EncryptHelper.sha256(loginRequest.password)

                val token = GenerateBearerToken.getToken()


                if (checkPassword) {

                    Database.tokenRepository.insertToken(
                        TokenEntity(
                            id = ObjectId(),
                            token = token,
                            expiry = LocalDateTime.now().plus(2, ChronoUnit.DAYS),
                            username = usersEntity.username
                    ))

                    return c.respond(
                        status = HttpStatusCode.OK,
                        message = LoginRespondModel(
                            status = true,
                            massageTH = "เข้าสู่ระบบสำเร็จ",
                            massageEN = "Login successful",
                            token = "bearer ${token.uppercase(Locale.getDefault())}"
                        )
                    )
                } else {
                    return c.respond(
                        status = HttpStatusCode.BadRequest,
                        message = RespondBasicModel(
                            status = false,
                            message = "Invalid password"
                        )
                    )
                }

            } ?: run {
                return c.respond(
                    status = HttpStatusCode.BadRequest,
                    message = RespondBasicModel(
                        status = false,
                        message = "Invalid credentials"
                    )
                )
            }
        } catch (e: Exception) {
            return c.respond(
                status = HttpStatusCode.InternalServerError,
                message = RespondBasicModel(
                    status = false,
                    message = e.toString()
                )
            )
        }
    }

    suspend fun userRegister(c: ApplicationCall) {
        try {
            val registerRequestModel = c.receive<RegisterRequestModel>()

            if (registerRequestModel.username.isNullOrBlank()) {
                return c.respond(
                    status = HttpStatusCode.BadRequest,
                    message = RespondBasicModel(
                        status = false,
                        message = "Username required"
                    )
                )
            }

            if (registerRequestModel.password.isNullOrBlank()) {
                return c.respond(
                    status = HttpStatusCode.BadRequest,
                    message = RespondBasicModel(
                        status = false,
                        message = "Password required"
                    )
                )
            }

            if (registerRequestModel.email.isNullOrBlank()) {
                return c.respond(
                    status = HttpStatusCode.BadRequest,
                    message = RespondBasicModel(
                        status = false,
                        message = "Email required"
                    )
                )
            }

            if (registerRequestModel.realName.isNullOrBlank() || registerRequestModel.familyName.isNullOrBlank()) {
                return c.respond(
                    status = HttpStatusCode.BadRequest,
                    message = RespondBasicModel(
                        status = false,
                        message = "Real Name Or Family Name required"
                    )
                )
            }

            val usersEntity: UsersEntity? = Database.userRepository.findUserByUsername(registerRequestModel.username)

            usersEntity?.let {
                return c.respond(
                    status = HttpStatusCode.BadRequest,
                    message = RespondBasicModel(
                        status = false,
                        message = "This username is already in the system."
                    )
                )
            } ?: run {

                val newPassword = EncryptHelper.sha256(registerRequestModel.password)

                val newMobile = ConvertMobilePhone.convertPhoneNumber(registerRequestModel.mobilePhone)

                val data = UsersEntity(
                    id = ObjectId(),
                    username = registerRequestModel.username,
                    password = newPassword,
                    email = registerRequestModel.email,
                    mobilePhone = newMobile,
                    registerDate = LocalDateTime.now()
                )

                Database.userRepository.insertUsers(data)

                return c.respond(
                    status = HttpStatusCode.OK,
                    message = RespondBasicModel(
                        status = false,
                        message = data.toString()
                    )
                )
            }
        } catch (e: Exception) {
            return c.respond(
                status = HttpStatusCode.InternalServerError,
                message = RespondBasicModel(
                    status = false,
                    message = e.toString()
                )
            )
        }
    }
}