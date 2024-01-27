package asia.raywel51.helper

import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.IOException
import javax.imageio.ImageIO

object GeneratorQRCode {
    fun generateQRCode(text: String, size: Int, iconWidth: Int, iconHeight: Int): BufferedImage {
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size)

        // Create a BufferedImage with a transparent background
        val image = BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB)
        val graphics = image.graphics as Graphics2D

        // Fill the background with a transparent color
        graphics.color = Color(0, 0, 0, 0) // Transparent color
        graphics.fillRect(0, 0, size, size)

        // Draw the QR code onto the image
        graphics.color = Color(0, 0, 0) // Black color
        for (x in 0 until size) {
            for (y in 0 until size) {
                if (bitMatrix.get(x, y)) {
                    graphics.fillRect(x, y, 1, 1)
                }
            }
        }

        // Load and resize the icon
        val icon = loadImageFromResources("logo.png")
        if (icon != null) {
            val resizedIcon = icon.getScaledInstance(iconWidth, iconHeight, BufferedImage.SCALE_SMOOTH)

            // Calculate the position to center the icon
            val x = (size - iconWidth) / 2
            val y = (size - iconHeight) / 2

            // Draw the resized icon onto the image
            graphics.drawImage(resizedIcon, x, y, null)
        }

        return image
    }

    private fun loadImageFromResources(fileName: String): BufferedImage? {
        return try {
            // Use class loader to read the file from resources
            val inputStream = this::class.java.classLoader.getResourceAsStream(fileName)
            if (inputStream != null) {
                ImageIO.read(inputStream)
            } else {
                null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun resizeImage(originalImage: BufferedImage, targetWidth: Int, targetHeight: Int): BufferedImage {
        // Create a new image with the desired size
        val resultingImage: Image = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT)

        // Create a buffered image with transparency
        val outputImage = BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB)

        // Draw the resized image
        outputImage.graphics.drawImage(resultingImage, 0, 0, null)

        return outputImage
    }
}