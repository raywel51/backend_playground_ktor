package asia.raywel51.handler.v2.reports

import asia.raywel51.model.RespondBasicModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import java.io.InputStream

class ReportHandler {
    suspend fun getTestReport(c: ApplicationCall) {

        val reportStream: InputStream = this::class.java.classLoader.getResourceAsStream("reports/Blank_A4.jrxml")
            ?: return c.respond(
                status = HttpStatusCode.BadRequest,
                message = RespondBasicModel(
                    status = false,
                    message = "Cannot Find Jasper Template"
                )
            )

        val jasperReport = JasperCompileManager.compileReport(reportStream)

        val data = listOf(SimpleReportData(_id = "12345", real_name = "123123", family_name = "123123", license_plate = "123445"))

        val dataSource = JRBeanCollectionDataSource(data)

        val jasperPrint = JasperFillManager.fillReport(jasperReport, mutableMapOf<String, Any>(), dataSource)

        val reportByteArray = JasperExportManager.exportReportToPdf(jasperPrint)
        c.respondBytes(reportByteArray, contentType = ContentType.Application.Pdf)
    }
}

data class SimpleReportData(
    val _id: String,
    val real_name: String,
    val family_name: String,
    val license_plate: String
)