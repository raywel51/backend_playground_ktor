package net.ddns.raywel.routes

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import net.ddns.raywel.ObjectIdSerializer
import net.ddns.raywel.handler.v2.qrcodes.QRCodeHandler
import net.ddns.raywel.handler.v2.reports.ReportHandler
import net.ddns.raywel.handler.v2.users.CredentialHandler

fun Application.configureRoutingV2() {

    routing {
        route("/v2") {
            route("/users") {
                val credentialHandler = CredentialHandler()
                post("login") { credentialHandler.userLogin(c = call) }
                post("register") { credentialHandler.userRegister(c = call) }
            }

            route("/qrcode") {
                val qrCodeHandler = QRCodeHandler()
                get("/gen-qr/{id}") { qrCodeHandler.generatorQrCode(c = call) }
            }

            route("/report") {
                val reportHandler = ReportHandler()
                get("/test") { reportHandler.getTestReport(c = call) }
            }
        }
    }
}