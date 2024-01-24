package net.ddns.raywel.routes

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import net.ddns.raywel.ObjectIdSerializer
import net.ddns.raywel.entity.UsersEntity
import net.ddns.raywel.model.RespondBasicModel
import net.ddns.raywel.repository.Database

fun Application.configureRouting() {

    install(AutoHeadResponse)
    install(ContentNegotiation) {
        json(Json {
            serializersModule = SerializersModule {
                contextual(ObjectIdSerializer)
            }
            ignoreUnknownKeys = true
        })
    }

    routing {


        get("/users") {
            val usersEntity: List<UsersEntity> = Database.userRepository.findAllUsers()
            println(usersEntity)
            call.respond(
                status = HttpStatusCode.OK,
                message = usersEntity
            )
        }

        route("/") {
            get {
                call.respondText("Welcome To Ktor!")
            }
            post {
                call.respondText("Welcome To Ktor!")
            }
        }

        route("/ping") {
            get {
                call.respondText("Welcome To Ktor!")
            }
            post {
                call.respondText("Welcome To Ktor!")
            }
        }

        route("{...}") {
            handle {

                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = RespondBasicModel(
                        status = false,
                        massage = "NotFoundHttpException"
                    )
                )
            }

        }
    }
}
