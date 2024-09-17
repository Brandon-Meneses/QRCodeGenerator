package com.brandevsolutions.qrgenerator.ui.functions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun createPdfWithQRCode(context: Context, qrCodeBitmap: Bitmap, fileName: String) {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // Tamaño A4 (595x842 puntos)
    val page = pdfDocument.startPage(pageInfo)

    val canvas: Canvas = page.canvas
    val paint = Paint()

    // Centrar el QR en la página
    val centerX = (pageInfo.pageWidth - qrCodeBitmap.width) / 2
    val centerY = (pageInfo.pageHeight - qrCodeBitmap.height) / 2
    canvas.drawBitmap(qrCodeBitmap, centerX.toFloat(), centerY.toFloat(), paint)

    pdfDocument.finishPage(page)

    // Guardar el PDF en la carpeta de Descargas
    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(downloadsDir, "$fileName.pdf")

    try {
        pdfDocument.writeTo(FileOutputStream(file))
        Toast.makeText(context, "PDF guardado en: ${file.absolutePath}", Toast.LENGTH_LONG).show()
    } catch (e: IOException) {
        e.printStackTrace()
        Toast.makeText(context, "Error al guardar PDF: ${e.message}", Toast.LENGTH_LONG).show()
    } finally {
        pdfDocument.close()
    }
}
