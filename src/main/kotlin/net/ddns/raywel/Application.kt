package net.ddns.raywel

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import net.ddns.raywel.routes.configureRouting
import net.ddns.raywel.routes.configureRoutingV2

fun main() {
    embeddedServer(CIO, port = 8080, host = "localhost", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureRoutingV2()
}
