package net.ddns.raywel.handler.v2.users

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import net.ddns.raywel.entity.UsersEntity
import net.ddns.raywel.helper.ConvertMobilePhone
import net.ddns.raywel.helper.EncryptHelper
import net.ddns.raywel.helper.GenerateBearerToken
import net.ddns.raywel.model.LoginRequestModel
import net.ddns.raywel.model.LoginRespondModel
import net.ddns.raywel.model.RegisterRequestModel
import net.ddns.raywel.model.RespondBasicModel
import net.ddns.raywel.repository.Database
import org.bson.types.ObjectId
import java.time.LocalDateTime
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
                        massage = "Username required"
                    )
                )
            }

            if (loginRequest.password.isNullOrBlank()) {
                return c.respond(
                    status = HttpStatusCode.BadRequest,
                    message = RespondBasicModel(
                        status = false,
                        massage = "Password are required"
                    )
                )
            }

            val usersEntity: UsersEntity? = Database.userRepository.findUserByUsername(loginRequest.username)

            usersEntity?.let {

                val checkPassword = it.password == EncryptHelper.sha256(loginRequest.password)

                val token = GenerateBearerToken.getToken()

                if (checkPassword) {
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
                            massage = "Invalid password"
                        )
                    )
                }

            } ?: run {
                return c.respond(
                    status = HttpStatusCode.BadRequest,
                    message = RespondBasicModel(
                        status = false,
                        massage = "Invalid credentials"
                    )
                )
            }
        } catch (e: Exception) {
            return c.respond(
                status = HttpStatusCode.InternalServerError,
                message = RespondBasicModel(
                    status = false,
                    massage = e.toString()
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
                        massage = "Username required"
                    )
                )
            }

            if (registerRequestModel.password.isNullOrBlank()) {
                return c.respond(
                    status = HttpStatusCode.BadRequest,
                    message = RespondBasicModel(
                        status = false,
                        massage = "Password required"
                    )
                )
            }

            if (registerRequestModel.email.isNullOrBlank()) {
                return c.respond(
                    status = HttpStatusCode.BadRequest,
                    message = RespondBasicModel(
                        status = false,
                        massage = "Email required"
                    )
                )
            }

            if (registerRequestModel.realName.isNullOrBlank() || registerRequestModel.familyName.isNullOrBlank()) {
                return c.respond(
                    status = HttpStatusCode.BadRequest,
                    message = RespondBasicModel(
                        status = false,
                        massage = "Real Name Or Family Name required"
                    )
                )
            }

            val usersEntity: UsersEntity? = Database.userRepository.findUserByUsername(registerRequestModel.username)

            usersEntity?.let {
                return c.respond(
                    status = HttpStatusCode.BadRequest,
                    message = RespondBasicModel(
                        status = false,
                        massage = "This username is already in the system."
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
                        massage = data.toString()
                    )
                )
            }
        } catch (e: Exception) {
            return c.respond(
                status = HttpStatusCode.InternalServerError,
                message = RespondBasicModel(
                    status = false,
                    massage = e.toString()
                )
            )
        }
    }
}