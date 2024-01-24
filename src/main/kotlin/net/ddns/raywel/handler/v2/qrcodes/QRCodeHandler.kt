package net.ddns.raywel.handler.v2.qrcodes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.ddns.raywel.helper.GeneratorQRCode
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

class QRCodeHandler {
    suspend fun generatorQrCode(c: ApplicationCall) {
        val id = c.parameters["id"]

        val qrCodeImage = GeneratorQRCode.generateQRCode(id.toString(), 340, 150, 150)

        val outputStream = ByteArrayOutputStream()
        withContext(Dispatchers.IO) {
            ImageIO.write(qrCodeImage, "png", outputStream)
        }
        val byteArray = outputStream.toByteArray()

        c.respondBytes(byteArray, ContentType.Image.PNG)
    }
}