package net.ddns.raywel.handler.v2.reports

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import java.io.File
import java.io.InputStream
import java.util.*
import kotlin.collections.HashMap

class ReportHandler {
    suspend fun getTestReport(c: ApplicationCall) {

        val reportStream: InputStream = this::class.java.classLoader.getResourceAsStream("reports/Blank_A4.jrxml")
            ?: throw IllegalArgumentException("Report template not found")

        val jasperReport = JasperCompileManager.compileReport(reportStream)

        // Sample data
        val data = listOf(SimpleReportData(_id = "12345", real_name = "123123", family_name = "123123", license_plate = "123445"))

        val dataSource = JRBeanCollectionDataSource(data)

        val jasperPrint = JasperFillManager.fillReport(jasperReport, mutableMapOf<String, Any>(), dataSource)

        // Export the report to PDF and return as byte array in the response
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