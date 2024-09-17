package com.brandevsolutions.qrgenerator.ui.functions

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

fun createPdfWithQRCode(context: Context, qrCodeBitmap: Bitmap, fileName: String) {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
    val page = pdfDocument.startPage(pageInfo)

    val canvas: Canvas = page.canvas
    val paint = Paint()

    // Centrar el QR en la página
    val centerX = (pageInfo.pageWidth - qrCodeBitmap.width) / 2
    val centerY = (pageInfo.pageHeight - qrCodeBitmap.height) / 2
    canvas.drawBitmap(qrCodeBitmap, centerX.toFloat(), centerY.toFloat(), paint)

    pdfDocument.finishPage(page)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // Usar MediaStore para Android 10 y superiores
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.pdf")
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            val outputStream = resolver.openOutputStream(it)
            outputStream?.let { stream ->
                pdfDocument.writeTo(stream)
                stream.close()
                Toast.makeText(context, "PDF guardado en Descargas", Toast.LENGTH_LONG).show()
            }
        }
    } else {
        // Para Android 9 y versiones anteriores
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadsDir, "$fileName.pdf")
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(context, "PDF guardado en: ${file.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Error al guardar PDF: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    pdfDocument.close()
}
