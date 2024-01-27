package asia.raywel51.routes

import asia.raywel51.entity.customSerializer.ObjectIdSerializer
import asia.raywel51.handler.v1.basic.BasicRespondHandler
import asia.raywel51.handler.v1.basic.WebViewHandler
import asia.raywel51.handler.v2.qrcodes.QRCodeHandler
import asia.raywel51.handler.v2.reports.ReportHandler
import asia.raywel51.handler.v1.users.CredentialHandler
import asia.raywel51.handler.v2.users.UsersHandler
import asia.raywel51.model.RespondBasicModel
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

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
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
    }

    val webViewHandler = WebViewHandler()
    val basicRespondHandler = BasicRespondHandler()
    val credentialHandler = CredentialHandler()
    val usersHandler = UsersHandler()
    val qrCodeHandler = QRCodeHandler()
    val reportHandler = ReportHandler()

    routing {

        get("/") { webViewHandler.welcomeView(c = call) }
        get("/ping") { basicRespondHandler.getPing(c = call) }

        post("login") { credentialHandler.userLogin(c = call) }
        post("register") { credentialHandler.userRegister(c = call) }

        route("/v2") {
            route("/users") {
                get  { usersHandler.getUserList(c = call) }
                get ( "" ) { usersHandler.getUserOne(c = call) }
                post ("/crates") { usersHandler.createUser(c = call) }
                put { usersHandler.editUser(c = call) }
                delete  ("") { usersHandler.deleteUser(c = call) }
            }

            route("/qrcode") {
                get("/gen-qr/{id}") { qrCodeHandler.generatorQrCode(c = call) }
            }

            route("/report") {
                get("/test") { reportHandler.getTestReport(c = call) }
            }
        }

        route("{...}") {
            handle {
                basicRespondHandler.getErrorPages(c = call)
            }

        }
    }
}
