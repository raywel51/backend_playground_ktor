package asia.raywel51.handler.v1.basic

import asia.raywel51.model.RespondBasicModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class BasicRespondHandler {
    suspend fun getPing(c: ApplicationCall) {
        c.respond(
            status = HttpStatusCode.OK,
            message = RespondBasicModel(
                status = true,
                message = "connection successfully"
            )
        )
    }

    suspend fun getErrorPages(c: ApplicationCall) {
        c.respond(
            status = HttpStatusCode.NotFound,
            message = RespondBasicModel(
                status = false,
                message = "NotFoundHttpException"
            )
        )
    }
}