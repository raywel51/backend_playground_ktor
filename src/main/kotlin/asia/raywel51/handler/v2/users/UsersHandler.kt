package asia.raywel51.handler.v2.users

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import asia.raywel51.entity.UsersEntity
import asia.raywel51.helper.CheckerToken
import asia.raywel51.model.RespondBasicModel
import asia.raywel51.repository.Database
import java.util.*

class UsersHandler {
    suspend fun getUserList(c: ApplicationCall) {
        try {
            val usersEntity: List<UsersEntity> = Database.userRepository.findAllUsers()
            println(usersEntity)
            c.respond(
                status = HttpStatusCode.OK,
                message = usersEntity
            )
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

    suspend fun getUserOne(c: ApplicationCall) {
        try {
            val username = c.request.queryParameters["username"]

            val usersEntity: UsersEntity? = Database.userRepository.findUserByUsername(username ?: "")

            c.respond(
                status = HttpStatusCode.OK,
                message = usersEntity.toString()
            )
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

    suspend fun createUser(c: ApplicationCall) {
        try {
            val usersEntity: List<UsersEntity> = Database.userRepository.findAllUsers()
            println(usersEntity)
            c.respond(
                status = HttpStatusCode.OK,
                message = usersEntity
            )
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

    suspend fun editUser(c: ApplicationCall) {
        try {
            val usersEntity: List<UsersEntity> = Database.userRepository.findAllUsers()
            println(usersEntity)
            c.respond(
                status = HttpStatusCode.OK,
                message = usersEntity
            )
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

    suspend fun deleteUser(c: ApplicationCall) {
        try {
            val token = c.request.headers["Authorization"]?.removePrefix("Bearer ")

            val username = c.request.queryParameters["username"]

            val checkToken = CheckerToken.checkToken(token)

            if (checkToken.status) {
                val deleteResult = Database.userRepository.deleteUserByUsername(username?.trim() ?: "")

                if (deleteResult) {
                    c.respond(
                        status = HttpStatusCode.OK,
                        message = RespondBasicModel(
                            status = false,
                            message = "User with ID $username deleted successfully"
                        )
                    )
                } else {
                    c.respond(
                        status = HttpStatusCode.NotFound,
                        message = RespondBasicModel(
                            status = false,
                            message = "User not found with ID $username"
                        )
                    )
                }
            } else {
                c.respond(
                    status = HttpStatusCode.NotFound,
                    message = RespondBasicModel(
                        status = false,
                        message = checkToken.message
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